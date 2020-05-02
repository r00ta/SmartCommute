package com.r00ta.telematics.android.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.r00ta.telematics.android.ClientConfig;
import com.r00ta.telematics.android.network.queue.QueueTripUpload;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class NetworkUpload {
    private static NetworkUpload instance;
    private Context ctx;

    private NetworkUpload(Context context) {
        ctx = context;
    }

    public static synchronized NetworkUpload getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkUpload(context);
        }
        return instance;
    }

    public void uploadAll() {
        Realm realmInstance = Realm.getDefaultInstance();
        RealmResults<QueueTripUpload> trips = realmInstance.where(QueueTripUpload.class).equalTo("isPending", false).sort("startTimestamp").findAll();
        realmInstance.close();
        for (QueueTripUpload trip : trips) {
            sendTrip(trip);
        }
    }

    private void sendTrip(QueueTripUpload trip) {
        String url = String.format( ClientConfig.BASE_HOST + ":1337/users/%s/trips/%s", trip.userId, trip.tripId);
        JSONObject body = null;
        try {
            body = new JSONObject().put("base64gzipNewTripRequest", trip.compressedTrip);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String reqTripId = trip.tripId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Upload", "ok");
                        new RemoveQueueAsync(reqTripId).execute();
                        Log.i("Upload", "successful");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("upload error", error.getMessage());
                        if (error.networkResponse != null){
                            Log.i("upload error", String.valueOf(error.networkResponse.statusCode));
                        }

                        new RequeueTripAsync(reqTripId).execute();
                        Log.i("upload", "FAILED");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                //headers.put("Authorization", "Bearer " + AuthManager.getInstance(ctx).getJwtToken());
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        HttpRequestProvider.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    private class RequeueTripAsync extends AsyncTask<String, String, String> {
        public String tripId;

        public RequeueTripAsync(String tripId) {
            this.tripId = tripId;
        }

        protected String doInBackground(String... params) {
            // Now in a background thread.

            // Open the Realm
            Realm realm = Realm.getDefaultInstance();
            try {
                // Work with Realm
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        QueueTripUpload dbTrip = bgRealm.where(QueueTripUpload.class).equalTo("tripId", tripId).findFirst();
                        dbTrip.isPending = false;
                    }
                });
            } finally {
                realm.close();
            }
            return null;
        }
    }

    private class RemoveQueueAsync extends AsyncTask<String, String, String> {
        public String tripId;

        public RemoveQueueAsync(String tripId) {
            this.tripId = tripId;
        }

        protected String doInBackground(String... params) {
            // Now in a background thread.

            // Open the Realm
            Realm realm = Realm.getDefaultInstance();
            try {
                // Work with Realm
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        QueueTripUpload dbTrip = bgRealm.where(QueueTripUpload.class).equalTo("tripId", tripId).findFirst();
                        dbTrip.deleteFromRealm();
                    }
                });
            } finally {
                realm.close();
            }
            return null;
        }
    }

}


