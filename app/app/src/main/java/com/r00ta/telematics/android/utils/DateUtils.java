package com.r00ta.telematics.android.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String fromTimestamp(Long timestamp){
        return new Date(timestamp).toString();
    }

    public static String getTimeInDay(Long timestamp){
        SimpleDateFormat df = new SimpleDateFormat("hh:mm");
        return df.format(new Date(timestamp));
    }

    public static String getDay(Long timestamp){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YY");
        return df.format(new Date(timestamp));
    }
}
