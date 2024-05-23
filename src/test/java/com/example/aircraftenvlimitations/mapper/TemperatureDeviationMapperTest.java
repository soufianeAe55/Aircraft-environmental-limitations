package com.example.aircraftenvlimitations.mapper;

import com.example.aircraftenvlimitations.dto.TemperatureDeviationResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class TemperatureDeviationMapperTest {

    @Test
    public void test_map(){
        //WHEN
        TemperatureDeviationResponse response=TemperatureDeviationMapper.map(40d,10d);
        //THEN
        assertEquals(Double.valueOf(40),response.getMaxTemperature());
        assertEquals(Double.valueOf(10),response.getMinTemperature());
    }

}