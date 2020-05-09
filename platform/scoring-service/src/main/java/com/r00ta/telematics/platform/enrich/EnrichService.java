package com.r00ta.telematics.platform.enrich;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.enrich.messaging.incoming.TripKafkaConsumer;
import com.r00ta.telematics.platform.enrich.messaging.outgoing.RouteAnalyticsKafkaProducer;
import com.r00ta.telematics.platform.enrich.messaging.outgoing.TripKafkaProducer;
import com.r00ta.telematics.platform.enrich.messaging.outgoing.dto.EnrichedTripSummaryDto;
import com.r00ta.telematics.platform.enrich.messaging.outgoing.dto.RouteAnalyticsDto;
import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import com.r00ta.telematics.platform.enrich.models.EnrichedTripHeader;
import com.r00ta.telematics.platform.enrich.models.TripModel;
import com.r00ta.telematics.platform.enrich.scoring.DriverScoring;
import com.r00ta.telematics.platform.enrich.storage.IEnrichStorageExtension;
import com.r00ta.telematics.platform.here.HereConfiguration;
import com.r00ta.telematics.platform.here.ReverseGeocoding;
import com.r00ta.telematics.platform.here.RouteMatching;
import com.r00ta.telematics.platform.here.models.geoaddress.HereGeoAddress;
import com.r00ta.telematics.platform.here.models.routing.RouteMatchModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EnrichService implements IEnrichService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripKafkaConsumer.class);
    private static final RouteMatching routeMatching = new RouteMatching(new HereConfiguration());
    private static final ReverseGeocoding reverseGeocoding = new ReverseGeocoding(new HereConfiguration());


    @Inject
    IEnrichStorageExtension storageExtension;

    @Inject
    TripKafkaProducer kafkaScoringProducer;

    @Inject
    RouteAnalyticsKafkaProducer routeAnalyticsKafkaProducer;

    @Override
    public EnrichedTrip processTrip(TripModel trip) {
        LOGGER.info(String.format("New trip %s going to be processed.", trip.tripId));
        if (trip.positions == null || trip.positions.isEmpty()){
            LOGGER.warn(String.format("New trip %s discarded 'cause it can not have empty gps positions.", trip.tripId));
            throw new IllegalArgumentException("Trip can't have empty gps positions.");
        }

        RouteMatchModel routeMatch = routeMatching.calculateRouteMatching(trip);
        LOGGER.info(String.format("New trip %s route matching successfully calculated.", trip.tripId));
        if (routeMatch.routeLinks.size() == 0 || routeMatch.tracePoints == null || routeMatch.tracePoints.isEmpty()) {
            LOGGER.warn(String.format("New trip %s route match empty", trip.tripId));
            return null;
        }

        // obd info are not stored!
        EnrichedTrip matchedTrip = EnrichedTrip.fromRouteMatch(trip.userId, trip.tripId, trip.routeId, routeMatch);

        Optional<HereGeoAddress> startAddress = reverseGeocoding.getAddressFromLocation(trip.positions.get(0));
        if (startAddress.isPresent()){
            LOGGER.info(String.format("New trip %s start location present.", trip.tripId));
            matchedTrip.startLocation = startAddress.get().city;
        }

        Optional<HereGeoAddress> endAddress = reverseGeocoding.getAddressFromLocation(trip.positions.get(trip.positions.size()-1));
        if (endAddress.isPresent()){
            LOGGER.info(String.format("New trip %s end location present", trip.tripId));
            matchedTrip.endLocation = endAddress.get().city;
        }

        DriverScoring.setPointScores(matchedTrip);
        LOGGER.info(String.format("New trip %s points calculated: %f", trip.tripId, matchedTrip.score));

        // calculate eco score if obd info is available; EcoScoring.score()

        LOGGER.info(String.format("New trip %s going to be stored", trip.tripId));
        boolean success = storageExtension.storeEnrichedTrip(matchedTrip);
        LOGGER.info(String.format("New trip %s stored: %s", trip.tripId, success));

        kafkaScoringProducer.sendEventAsync(new EnrichedTripSummaryDto(matchedTrip));
        LOGGER.info(String.format("New trip %s sent to user service for statistics.", trip.tripId));

        routeAnalyticsKafkaProducer.sendEventAsync(new RouteAnalyticsDto(matchedTrip));
        LOGGER.info(String.format("New trip %s going to route analytics service.", trip.tripId));

        return matchedTrip;
    }

    @Override
    public Optional<EnrichedTrip> getTrip(String userId, String tripId) {
        return storageExtension.getTripById(tripId);
    }

    @Override
    public List<EnrichedTripHeader> getTripsHeadersByTimeRange(String userId, Long from, Long to) {
        return storageExtension.getTripsHeadersByTimeRange(userId, from, to);
    }
}
