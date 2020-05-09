package com.r00ta.telematics.android.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.android.ClientConfig;
import com.r00ta.telematics.android.responses.AuthenticationResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthManager {
    private static AuthManager instance;
    private Context ctx;
    private String jwtToken;
    private String userId;
    private int loginFailedCount = 0;
    private Boolean authenticationPending = false;

    private AuthManager(Context context) {
        ctx = context;
        updateInternalJwtToken();
    }

    public static synchronized AuthManager getInstance(Context context) {
        if (instance == null) {
            instance = new AuthManager(context);
        }
        return instance;
    }

    public String getJwtToken(){
        return jwtToken;
    }

    public String getUserId(){
        return userId;
    }

    public synchronized void getNewJwtToken(){
        synchronized (authenticationPending){
            if(authenticationPending){
                return;
            }
            else{
                authenticationPending = true;
            }
        }

        SharedPreferences smartCommutePreferences = ctx.getSharedPreferences("smartCommutePreferences", Context.MODE_PRIVATE);
        String email = smartCommutePreferences.getString("email", "0");
        String password = smartCommutePreferences.getString("password", "0");

        String url =  ClientConfig.BASE_HOST + ":1337/users/auth";
        JSONObject body = null;
        try {
            body = new JSONObject().put("email", email).put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        AuthenticationResponse authentication = null;
                        try {
                            authentication = new ObjectMapper().readValue(response.toString(), AuthenticationResponse.class);
                            onLoginSuccess(authentication);
                        } catch (JsonProcessingException e) {
                            onLoginFailed();
                            Log.i("Login", "FAILED");
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        onLoginFailed();
                        Log.i("Login", "FAILED");
                    }
                });
        HttpRequestProvider.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    private void onLoginFailed(){
        try {
            Thread.sleep(1000 * 15); // sleep 15 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (loginFailedCount++ > 15){
            requestLoginAtStartup(true);
            authenticationPending = false;
            return;
        }
        getNewJwtToken();
    }

    private void requestLoginAtStartup(boolean value){
        SharedPreferences smartCommutePreferences = ctx.getSharedPreferences("smartCommutePreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = smartCommutePreferences.edit();
        editor.putBoolean("isLoginNeeded", value);
        editor.commit();
    }

    private void onLoginSuccess(AuthenticationResponse response){
        SharedPreferences smartCommutePreferences = ctx.getSharedPreferences("smartCommutePreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = smartCommutePreferences.edit();
        editor.putString("userId", response.userId);
        editor.putString("jwtBearer", response.jwtBearer);
        editor.commit();
        updateInternalJwtToken();
        requestLoginAtStartup(false);
        authenticationPending = false;
    }

    private void updateInternalJwtToken(){
        SharedPreferences smartCommutePreferences = ctx.getSharedPreferences("smartCommutePreferences", Context.MODE_PRIVATE);
        jwtToken = smartCommutePreferences.getString("jwtBearer", "0");
        userId = smartCommutePreferences.getString("userId", "0");
    }
}
