package com.r00ta.telematics.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.android.network.NetworkUpload;
import com.r00ta.telematics.android.network.models.TripModelDto;
import com.r00ta.telematics.android.network.queue.QueueTripUpload;
import com.r00ta.telematics.android.persistence.models.TripModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    public static void closeAllSessions(Context context, Realm realmInstance) {
        SharedPreferences smartCommutePreferences = context.getSharedPreferences("smartCommutePreferences", Context.MODE_PRIVATE);
        String userId = smartCommutePreferences.getString("userId", "0");
        realmInstance.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TripModel> trips = realm.where(TripModel.class).equalTo("isFinished", false).findAll();
                for (TripModel trip : trips) {
                    try {
                        TripModelDto modelDto = new TripModelDto(trip);
                        String base64GzippedTrip = Base64.encodeToString(compressGZIP(mapper.writeValueAsString(modelDto)), Base64.DEFAULT);
                        realm.copyToRealmOrUpdate(new QueueTripUpload(trip.tripId, userId, base64GzippedTrip, modelDto.startTimestamp));
//                        queueTripUpload.tripId = modelDto.tripId;
//                        queueTripUpload.compressedTrip = base64GzippedTrip;
                        trip.isFinished = true;
                        Log.i("Realm", "Trip " + trip.tripId + " has been enqueued successfully");
                    } catch (IOException e) {
                        trip.transformationFailures += 1;
                        e.printStackTrace();
                    }
                }

                // Delete all the trips that have failure counts > 5
                RealmResults<TripModel> corruptedTrips = realm.where(TripModel.class).greaterThan("transformationFailures", 5).findAll();
                Log.i("Realm", "Delete " + corruptedTrips.size() + " corrupted trips");
                corruptedTrips.deleteAllFromRealm();

                RealmResults<TripModel> enqueuedTrips = realm.where(TripModel.class).equalTo("isFinished", true).findAll();
                Log.i("Realm", "Delete " + enqueuedTrips.size() + " enqueued trips");
                enqueuedTrips.deleteAllFromRealm();

                NetworkUpload.getInstance(context).uploadAll();

                Log.i("LEN : ", String.valueOf(trips.size()));
            }
        });
    }

    public static byte[] compressGZIP(String input) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream out = new GZIPOutputStream(outputStream)) {
            try (ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }
        }
        return outputStream.toByteArray();
    }
}
