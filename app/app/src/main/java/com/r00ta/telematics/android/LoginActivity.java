package com.r00ta.telematics.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.android.network.HttpRequestProvider;
import com.r00ta.telematics.android.responses.AuthenticationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                // signum instead main
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailedFormatParameters();
            return;
        }

        _loginButton.setEnabled(false);

        String email = _emailText.getText().toString().toLowerCase();
        String password = _passwordText.getText().toString();

        Context mContext = getApplicationContext();
        SharedPreferences smartCommutePreferences = mContext.getSharedPreferences("smartCommutePreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = smartCommutePreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();

        Log.d(password, "CREATE");

        String url = "http://10.0.2.2:1337/users/auth";
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
                        } catch (JsonProcessingException e) {
                            onLoginFailed();
                            Log.i("Login", "FAILED");
                            return;
                        }
                        onLoginSuccess(authentication);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        onLoginFailed();
                        Log.i("Login", "FAILED");
                    }
                });
        HttpRequestProvider.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


    public void onLoginSuccess(AuthenticationResponse authenticationResponse){
        Context mContext = getApplicationContext();
        SharedPreferences smartCommutePreferences = mContext.getSharedPreferences("smartCommutePreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = smartCommutePreferences.edit();
        editor.putString("userId", authenticationResponse.userId);
        editor.putString("jwtBearer", authenticationResponse.jwtBearer);
        editor.commit();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginFailedFormatParameters() {
        _loginButton.setEnabled(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _emailText.getText().toString().toLowerCase();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _emailText.setError("username cannot be empty");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
