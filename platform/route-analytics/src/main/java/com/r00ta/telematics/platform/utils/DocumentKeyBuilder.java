package com.r00ta.telematics.platform.utils;

import java.time.LocalDate;

public class DocumentKeyBuilder {

    public static String build(String userId, String routeId){
        LocalDate currentDate = LocalDate.now();

        String day = String.valueOf(currentDate.getDayOfMonth());
        String month = String.valueOf(currentDate.getMonth().getValue());
        String year = String.valueOf(currentDate.getYear());

        return String.format("%s/%s/%s/%s_%s.json", year, year + month, year + month + day, userId, routeId);
    }
}
