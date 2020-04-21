package com.r00ta.telematics.platform;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r00ta.telematics.platform.models.AnalyticsRoute;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AnalyticsService implements IAnalyticsService {

    @Inject
    IDataLakeUploader dataLakeUploader;

    @Override
    public void processRoute(AnalyticsRoute route) {
        try {
            dataLakeUploader.uploadRoute(route);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // upload to ibm storage
    }

    @Override
    public void processAnalysisResults(String resultsURL) {
        // load from ibm storage
    }
}
