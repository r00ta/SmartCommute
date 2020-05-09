package com.r00ta.telematics.android.ui.trips;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.r00ta.telematics.android.ClientConfig;
import com.r00ta.telematics.android.R;
import com.r00ta.telematics.android.network.AuthManager;
import com.r00ta.telematics.android.network.HttpRequestProvider;
import com.r00ta.telematics.android.network.models.trips.EnrichedGpsLocation;
import com.r00ta.telematics.android.network.models.trips.EnrichedTripResponse;
import com.r00ta.telematics.android.persistence.retrieved.trips.EnrichedGpsPosition;
import com.r00ta.telematics.android.persistence.retrieved.trips.Trip;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

public class TripDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String tripId;
    private GoogleMap googleMap;
    private Realm realmInstance;

    private Activity act = this;

    private static final String TAG = "TripDetailsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realmInstance = Realm.getDefaultInstance();
        Bundle b = getIntent().getExtras();
        if (b != null)
            tripId = b.getString("tripId");

        setContentView(R.layout.activity_trip_details);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.tripDetailsMapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        Trip trip = getLocalTrip(tripId);
        if (trip == null) { // new trip has to be fetched
            fetchTripFromServerAndDisplay(tripId);
        } else {
            showTripOnMap(trip);
        }
    }

    private void showTripOnMap(Trip trip) {
        act.runOnUiThread(new Runnable() {
            public void run() {
                if (trip == null || trip.positions == null || trip.positions.isEmpty()) {
                    return;
                }

                LatLng first = new LatLng(trip.positions.get(0).latitude, trip.positions.get(0).longitude);
                LatLng last = first;
                for (EnrichedGpsPosition position : trip.positions) {
                    LatLng current = new LatLng(position.latitude, position.longitude);
                    googleMap.addPolyline(new PolylineOptions().add(last, current).width(9).color(DisplayColorFactory.getColor(position.pointScore)).geodesic(true));
                    last = current;
                }
                LatLngBounds build = new LatLngBounds.Builder().include(first).include(last).build();
                googleMap.addMarker(new MarkerOptions()
                        .position(first)
                        .title("Start"));
                googleMap.addMarker(new MarkerOptions()
                        .position(last)
                        .title("End"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(build, 10));
            }
        });
    }

    private Trip getLocalTrip(String tripId) {
        return realmInstance.where(Trip.class).equalTo("tripId", tripId).findFirst();
    }

    private void fetchTripFromServerAndDisplay(String tripId) {
        String url = String.format(ClientConfig.BASE_HOST + ":1338/users/%s/enrichedTrips/%s", AuthManager.getInstance(getApplicationContext()).getUserId(), tripId);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Fetched trips", "ok");
                        try {
                            EnrichedTripResponse headers = new ObjectMapper().readValue(response.toString(), EnrichedTripResponse.class);
                            new UpdateTripCollection(headers).execute();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        Log.i("Upload", "successful");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("Retrieve trip error", error.getMessage());
                        if (error.networkResponse != null) {
                            Log.i("Retrieve trip ", String.valueOf(error.networkResponse.statusCode));
                        }
                        Log.i("Retrieve trip ", "FAILED");
                        Toast.makeText(getApplicationContext(), "Check your connectivity.", Toast.LENGTH_LONG);
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
        HttpRequestProvider.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }


    private class UpdateTripCollection extends AsyncTask<String, String, String> {
        public EnrichedTripResponse trip;

        public UpdateTripCollection(EnrichedTripResponse trip) {
            this.trip = trip;
        }

        protected String doInBackground(String... params) {
            // Now in a background thread.
            Realm realm = Realm.getDefaultInstance();
            final Trip[] tripToStore = {null};
            try {
                // Work with Realm
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        tripToStore[0] = Trip.fromDto(trip);
                        Log.i("SUCA", String.valueOf(trip.positions.size()));
                        for (EnrichedGpsLocation location : trip.positions) {
                            EnrichedGpsPosition position = EnrichedGpsPosition.fromDto(location);
                            bgRealm.copyToRealmOrUpdate(position);
                            tripToStore[0].positions.add(position);
                        }
                        bgRealm.copyToRealmOrUpdate(tripToStore[0]);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                realm.close();
                showTripOnMap(tripToStore[0]);
            }

            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "On pause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "On stop");
        super.onStop();
    }

}
