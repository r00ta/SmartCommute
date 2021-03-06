package com.r00ta.telematics.platform;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r00ta.telematics.platform.models.AnalyticsRoute;

@ApplicationScoped
public class AnalyticsService implements IAnalyticsService {

    @Inject
    IDataLakeProvider dataLakeProvider;

    @Override
    public void processRoute(AnalyticsRoute route) {
        try {
            dataLakeProvider.uploadRoute(route);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // upload to ibm storage
    }

    @Override
    public void processAnalysisResults(String itemName) {
        dataLakeProvider.readAnalysisResults(itemName);
        // do stuff, push to user service.
    }
}
