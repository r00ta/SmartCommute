package com.r00ta.telematics.android.ui.trips;

import android.graphics.Color;

public class DisplayColorFactory {
    public static int getColor(Float score){
        if (score >= 0){
            return Color.parseColor("#00bf59");
        }
        if (score >= -1f){
            return Color.parseColor("#a9bf00");
        }
        if (score >= -5f){
            return Color.parseColor("#bf7600");
        }
        return Color.parseColor("#bf0000");
    }
}
