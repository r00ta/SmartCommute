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
        RouteMatchModel routeMatch = routeMatching.calculateRouteMatching(trip);
        LOGGER.info("Route match calculated");
        if (routeMatch.routeLinks.size() == 0) {
            LOGGER.warn("Routematch without any match, the trip is discarded.");
            return null;
        }

        // obd info are not stored!
        EnrichedTrip matchedTrip = EnrichedTrip.fromRouteMatch(trip.userId, trip.tripId, trip.routeId, routeMatch);

        Optional<HereGeoAddress> startAddress = reverseGeocoding.getAddressFromLocation(trip.positions.get(0));
        if (startAddress.isPresent()){
            matchedTrip.startLocation = startAddress.get().city;
        }

        Optional<HereGeoAddress> endAddress = reverseGeocoding.getAddressFromLocation(trip.positions.get(trip.positions.size()-1));
        if (endAddress.isPresent()){
            matchedTrip.endLocation = endAddress.get().city;
        }

        LOGGER.info("Enriched trip created");
        DriverScoring.setPointScores(matchedTrip);

        // calculate eco score if obd info is available; EcoScoring.score()

        storageExtension.storeEnrichedTrip(matchedTrip);
        LOGGER.info("Enrichedtrip stored");

        kafkaScoringProducer.sendEventAsync(new EnrichedTripSummaryDto(matchedTrip));

        routeAnalyticsKafkaProducer.sendEventAsync(new RouteAnalyticsDto(matchedTrip));

        return matchedTrip;
    }

    @Override
    public EnrichedTrip storeTrip(String userId, TripModel trip) {
        EnrichedTrip enrichedTrip = processTrip(trip);
        return enrichedTrip;
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
