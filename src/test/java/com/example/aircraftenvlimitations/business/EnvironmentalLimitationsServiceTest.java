package com.example.aircraftenvlimitations.business;

import com.example.aircraftenvlimitations.dto.TemperatureDeviationResponse;
import com.example.aircraftenvlimitations.exception.BusinessException;
import com.example.aircraftenvlimitations.model.AeroPhase;
import com.example.aircraftenvlimitations.model.EnvironmentalLimitationsModel;
import com.example.aircraftenvlimitations.validation.TemperatureDeviationValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnvironmentalLimitationsServiceTest {

    @InjectMocks
    private EnvironmentalLimitationsService service;
    @Mock
    private CsvService csvService;
    @Mock
    private TemperatureDeviationValidator validator;

    @Test
    public void getTemperatureDeviation_should_be_ok() throws BusinessException {
        //GIVEN
        EnvironmentalLimitationsModel model=getMockedModel();
        //WHEN
        doNothing().when(validator).validate(any(),anyDouble(),any());
        doReturn(model).when(csvService).loadDataByAirCraftAndAeroPhase(anyString(),any());
        TemperatureDeviationResponse response=service.getTemperatureDeviation(model.getAirCraftModel(), 35000.0, AeroPhase.CRUISE);
        //THEN
        assertNotNull(response);
        assertEquals(Double.valueOf(25.0), response.getMaxTemperature());
        assertEquals(Double.valueOf(-45.0), response.getMinTemperature());
    }
    @Test
    public void getTemperatureDeviation_should_return_null_values_if_altitude_is_outOfRange() throws BusinessException {
        //GIVEN
        EnvironmentalLimitationsModel model=getMockedModel();
        //WHEN
        doNothing().when(validator).validate(any(),anyDouble(),any());
        doReturn(model).when(csvService).loadDataByAirCraftAndAeroPhase(anyString(),any());
        TemperatureDeviationResponse response=service.getTemperatureDeviation(model.getAirCraftModel(),10000.0, AeroPhase.CRUISE);
        //THEN
        assertNotNull(response);
        assertNull(response.getMaxTemperature());
        assertNull(response.getMinTemperature());
    }
    @Test
    public void getTemperatureByAltitude_should_be_ok(){
        //GIVEN
        Map<Double, Double> temperatureMap=Map.of(30000.0, 30.0, 35000.0, 25.0, 40000.0, 20.0);
        //WHEN
        Double response=service.getTemperatureByAltitude(temperatureMap,40000.0);
        //THEN
        assertNotNull(response);
        assertEquals(Double.valueOf(20.0), response);

    }
    @Test
    public void getTemperatureByAltitude_should_return_null_if_temperatureMap_is_empty(){
        //GIVEN
        Map<Double, Double> temperatureMap=new HashMap<>();
        //THEN
        Double response=service.getTemperatureByAltitude(temperatureMap,2000.0);
        //WHEN
        assertNull(response);
    }
    @Test
    public void getTemperatureByAltitude_should_return_null_if_temperatureMap_is_null(){
        //WHEN
        Double response=service.getTemperatureByAltitude(null,2000.0);
        //THEN
        assertNull(response);
    }
    @Test
    public void getTemperatureByAltitude_should_return_null_if_altitude_is_outOfRange(){
        //GIVEN
        Map<Double, Double> temperatureMap=Map.of(30000.0, 30.0, 35000.0, 25.0, 40000.0, 20.0);
        //WHEN
        Double response=service.getTemperatureByAltitude(temperatureMap,2000.0);
        //THEN
        assertNull(response);
    }

    public EnvironmentalLimitationsModel getMockedModel(){
        return EnvironmentalLimitationsModel.builder()
                .airCraftModel("B772LR")
                .maxTemperature(Map.of(30000.0, 30.0, 35000.0, 25.0, 40000.0, 20.0))
                .minTemperature(Map.of(30000.0, -40.0, 35000.0, -45.0, 40000.0, -50.0))
                .build();
    }
}