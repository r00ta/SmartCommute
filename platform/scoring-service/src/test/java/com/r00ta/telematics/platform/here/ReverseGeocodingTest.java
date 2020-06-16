package com.r00ta.telematics.platform.here;

import com.r00ta.telematics.platform.enrich.models.GpsLocation;
import com.r00ta.telematics.platform.here.models.geoaddress.HereGeoAddress;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(KafkaTestResource.class)
public class ReverseGeocodingTest {
    @ConfigProperty(name = "here.appKey")
    public String appKey;
    @ConfigProperty(name = "here.apiKey")
    public String apiKey;

    @Test
    public void GivenAGpsLocation_WhenReverseGeoCodeIsCalled_ThenTheAddressIsRetrieved() {
        ReverseGeocoding reverseGeocoding = new ReverseGeocoding(new HereConfiguration(appKey, apiKey));
        HereGeoAddress address = reverseGeocoding.getAddressFromLocation(new GpsLocation(45.619208, 9.040203)).get();
        Assertions.assertEquals("Saronno", address.city);
        Assertions.assertEquals("Via Filippo Reina", address.street);
        Assertions.assertEquals("21047", address.postalCode);
        Assertions.assertEquals("ITA", address.country);
    }
}
