package com.r00ta.telematics.platform.routes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.routes.models.MatchingPendingStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatchingPendingStatusTest {

    @Test
    public void GivenANewRouteRequest_WhenARouteIsStored_ThenItIsAvailable() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        MatchingPendingStatus status = MatchingPendingStatus.ACCEPTED;
        String serialization = mapper.writeValueAsString(status);

        Assertions.assertEquals("\"ACCEPTED\"", serialization);
    }
}
