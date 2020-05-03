package com.r00ta.telematics.platform.here;

import com.r00ta.telematics.platform.enrich.models.GpsLocation;
import com.r00ta.telematics.platform.here.models.geoaddress.HereGeoAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReverseGeocodingTest {

    @Test
    public void GivenAGpsLocation_WhenReverseGeoCodeIsCalled_ThenTheAddressIsRetrieved() {
        ReverseGeocoding reverseGeocoding = new ReverseGeocoding(new HereConfiguration());
        HereGeoAddress address = reverseGeocoding.getAddressFromLocation(new GpsLocation(45.619208, 9.040203)).get();
        Assertions.assertEquals("Saronno", address.city);
        Assertions.assertEquals("Via Filippo Reina", address.street);
        Assertions.assertEquals("21047", address.postalCode);
        Assertions.assertEquals("ITA", address.country);
    }
}
