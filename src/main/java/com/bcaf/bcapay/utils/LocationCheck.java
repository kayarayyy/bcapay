package com.bcaf.bcapay.utils;

import org.springframework.stereotype.Component;

@Component
public class LocationCheck {
    public static double countDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius bumi dalam kilometer
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
    
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
    
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Jarak dalam kilometer
    }

    public boolean isOutsideIndonesia(double latitude, double longitude) {
        return latitude < -11.0 || latitude > 6.0 || longitude < 95.0 || longitude > 141.0;
    }    
}
