package com.example.aircraftenvlimitations.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AltitudeTemperatureUtilTest {

    @Test
    public void binarySearch_should_return_temperature_when_is_found_by_altitude(){
        //GIVEN
        Map<Double, Double> temperatureMap=Map.of(30000.0, 30.0, 35000.0, 25.0, 40000.0, 20.0);
        //WHE
        Double response=AltitudeTemperatureUtil.binarySearch(temperatureMap,new ArrayList<>(temperatureMap.keySet()),35000.0);
        //THEN
        assertEquals(Double.valueOf(25.0), response);
    }
    @Test
    public void binarySearch_should_calculate_and_return_temperature_if_is_not_found_by_altitude(){
        //GIVEN
        Map<Double, Double> temperatureMap=Map.of(30000.0, 30.0, 35000.0, 25.0, 40000.0, 20.0);
        //WHE
        Double response=AltitudeTemperatureUtil.binarySearch(temperatureMap,new ArrayList<>(temperatureMap.keySet()),39000.0);
        //THEN
        assertEquals(Double.valueOf(21.0), response);
    }


}