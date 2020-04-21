package com.r00ta.telematics.platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r00ta.telematics.platform.models.AnalyticsRoute;

public interface IDataLakeUploader {
    boolean uploadRoute(AnalyticsRoute route) throws JsonProcessingException;

//    boolean deleteRoute(String userId, String routeId);

}
