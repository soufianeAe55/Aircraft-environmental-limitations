package com.example.aircraftenvlimitations.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Utility class to override some features like binarySearch in this case
 */
public interface AltitudeTemperatureUtil {

    static Double binarySearch(Map<Double, Double> map, List<Double> altitudes, Double altitude){
        int index= Collections.binarySearch(altitudes, altitude);
        if(index>=0) return map.get(altitude);
        int insertionPoint = -index - 1;
        double lowerAltitude = altitudes.get(insertionPoint-1);
        double higherAltitude = altitudes.get(insertionPoint);
        double lowerTemperature = map.get(lowerAltitude);
        double higherTemperature = map.get(higherAltitude);
        return lowerTemperature + (altitude - lowerAltitude)*(higherTemperature -lowerTemperature)
                / (higherAltitude - lowerAltitude);
    }
}
