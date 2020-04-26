package com.r00ta.telematics.platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r00ta.telematics.platform.models.AnalysisResults;
import com.r00ta.telematics.platform.models.AnalyticsRoute;

public interface IDataLakeProvider {

    boolean uploadRoute(AnalyticsRoute route) throws JsonProcessingException;

    AnalysisResults readAnalysisResults(String itemName);
//    boolean deleteRoute(String userId, String routeId);
}
