package com.r00ta.telematics.android.ui.trips;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.r00ta.telematics.android.ClientConfig;
import com.r00ta.telematics.android.R;
import com.r00ta.telematics.android.RecordingTripActivity;
import com.r00ta.telematics.android.network.AuthManager;
import com.r00ta.telematics.android.network.HttpRequestProvider;
import com.r00ta.telematics.android.network.NetworkUpload;
import com.r00ta.telematics.android.network.models.trips.EnrichedTripHeader;
import com.r00ta.telematics.android.network.models.trips.EnrichedTripHeadersResponse;
import com.r00ta.telematics.android.persistence.retrieved.trips.TripHeader;
import com.r00ta.telematics.android.ui.RecyclerViewClickListener;
import com.r00ta.telematics.android.utils.DateUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.Sort;

public class TripsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private Realm realm;
    private TripsRecyclerViewAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();

        View root = inflater.inflate(R.layout.fragment_trips, container, false);
        final FloatingActionButton recordTripBtn = (FloatingActionButton) root.findViewById(R.id.recordOccasionalTrip);

        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeToRefreshTrips);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) root.findViewById(R.id.trips_recycle_view);

        setUpRecyclerView();

        recordTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecordingTripActivity.class);
                startActivity(intent);
            }
        });

        onRefresh();

//        tripsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onRefresh() {
        NetworkUpload.getInstance(getContext()).uploadAll();
        fetchTripsAndUpdateUI();
        //        Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    private void fetchTripsAndUpdateUI() {
        Number trips = realm.where(TripHeader.class).max("startTimestamp");
        if (trips == null) { //empty db
            retrieveTrips(0L, 2535197367000L);
        }
        else{
            retrieveTrips(trips.longValue() - 604800*1000, 2535197367000L); // 1 week before the last available trip in the db
        }
    }

    private void retrieveTrips(Long from, Long to){
        String url = String.format( ClientConfig.BASE_HOST + ":1338/users/%s/enrichedTrips?from=%d&to=%d", AuthManager.getInstance(getContext()).getUserId(), from, to);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Fetched trips", "ok");
                        try {
                            EnrichedTripHeadersResponse headers = new ObjectMapper().readValue(response.toString(), EnrichedTripHeadersResponse.class);
                            new UpdateTripHeaderCollection(headers).execute();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        Log.i("Upload", "successful");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("Retrieve trip ", "FAILED");
                        if (error.networkResponse != null){
                            Log.i("Retrieve trip ", String.valueOf(error.networkResponse.statusCode));
                        }
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
        HttpRequestProvider.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void setUpRecyclerView() {
        RecyclerViewClickListener listener = (view, position) -> { // open view on the trip! TODO
            Intent intent = new Intent(getActivity(), TripDetailsActivity.class);
            Bundle b = new Bundle();
            b.putString("tripId", adapter.getItem(position).tripId); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            Toast.makeText(getContext(), adapter.getItem(position).tripId, Toast.LENGTH_SHORT).show();
        };

        adapter = new TripsRecyclerViewAdapter(realm.where(TripHeader.class).sort("id", Sort.DESCENDING).findAll(), listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback {

        TouchHelperCallback() {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);//ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            //DataHelper.deleteItemAsync(realm, viewHolder.getItemId());
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
    }

    private class UpdateTripHeaderCollection extends AsyncTask<String, String, String> {
        public EnrichedTripHeadersResponse headers;

        public UpdateTripHeaderCollection(EnrichedTripHeadersResponse headers) {
            this.headers = headers;
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
                        for (EnrichedTripHeader h : headers.enrichedTrips){
                            TripHeader isPresent = bgRealm.where(TripHeader.class).equalTo("id", h.startTimestamp).findFirst();
                            if (isPresent == null){
                                bgRealm.copyToRealmOrUpdate(new TripHeader(h.startTimestamp, h.startLocation, h.endLocation, DateUtils.getTimeInDay(h.startTimestamp), DateUtils.getTimeInDay(h.startTimestamp + h.durationInMilliseconds), DateUtils.getDay(h.startTimestamp), String.valueOf(h.distanceInM/1000), h.tripId, h.startTimestamp));
                            }
                        }
                    }
                });
            }
            catch (Exception e){
            }
            finally {
                realm.close();
            }

            recyclerView.smoothScrollToPosition(0);
            return null;
        }
    }

}
