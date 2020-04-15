package com.r00ta.telematics.platform.here;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.r00ta.telematics.platform.enrich.models.TripModel;
import io.jenetics.jpx.GPX;

public class GpxFactory {

    public static String getGpxAsString(TripModel trip) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            GPX.write(GPX.builder()
                              .addTrack(track -> track
                                      .addSegment(segment ->
                                                          trip.positions.stream().forEach(x -> segment.addPoint(p -> p.lat(x.latitude).lon(x.longitude).speed(x.speed).time(x.timestamp)))))
                              .build(), stream);
            return new String(stream.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
