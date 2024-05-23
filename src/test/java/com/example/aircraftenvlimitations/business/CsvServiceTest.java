package com.example.aircraftenvlimitations.business;

import com.example.aircraftenvlimitations.exception.BusinessException;
import com.example.aircraftenvlimitations.exception.TechnicalException;
import com.example.aircraftenvlimitations.model.AeroPhase;
import com.example.aircraftenvlimitations.model.EnvironmentalLimitationsModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



@RunWith(MockitoJUnitRunner.class)
public class CsvServiceTest {

    @InjectMocks
    private CsvService csvService;

    @Mock
    private ResourceLoader resourceLoader;

    final private String AIRCRAFT_NOT_FOUND="aircraft.not.found";


    @Test
    public void loadDataByAirCraftAndAeroPhase_should_be_ok_with_AeroPhase_flight() throws IOException, BusinessException {
        //GIVEN
        String csvContent=getCsvContent();
        Resource resource=Mockito.mock(Resource.class);
        //WHEN
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(csvContent.getBytes()));
        EnvironmentalLimitationsModel model=csvService.loadDataByAirCraftAndAeroPhase("F70", AeroPhase.CRUISE);
        //THEN
        assertNotNull(model.getAirCraftModel());
        assertFalse(model.getMaxTemperature().isEmpty());
        assertFalse(model.getMinTemperature().isEmpty());
    }
    @Test
    public void loadDataByAirCraftAndAeroPhase_should_be_ok_with_AeroPhase_TOLD() throws IOException, BusinessException {
        //GIVEN
        String csvContent=getCsvContent();
        Resource resource=Mockito.mock(Resource.class);
        //WHEN
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(csvContent.getBytes()));
        EnvironmentalLimitationsModel model=csvService.loadDataByAirCraftAndAeroPhase("F70", AeroPhase.TAKEOFF);
        //THEN
        assertNotNull(model.getAirCraftModel());
        assertFalse(model.getMaxTemperature().isEmpty());
        assertFalse(model.getMinTemperature().isEmpty());
    }
    @Test
    public void loadDataByAirCraftAndAeroPhase_should_throw_business_exception_when_aircraft_is_not_found() throws IOException {
        //GIVEN
        String csvContent=getCsvContent();
        Resource resource=Mockito.mock(Resource.class);
        //WHEN
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(csvContent.getBytes()));
        BusinessException exception=assertThrows(BusinessException.class,()->{
             csvService.loadDataByAirCraftAndAeroPhase("F90YYY", AeroPhase.TAKEOFF);
        });
        //THEN
        assertEquals(AIRCRAFT_NOT_FOUND,exception.getMessageList().get(0).getKey());
    }
    @Test
    public void loadDataByAirCraftAndAeroPhase_should_throw_technical_exception_when_file_not_parsed()  {
        //WHEN
        when(resourceLoader.getResource(anyString())).thenThrow(new TechnicalException("err.tech"));
        //THEN
        assertThrows(TechnicalException.class,()->{
            csvService.loadDataByAirCraftAndAeroPhase("F70", AeroPhase.TAKEOFF);
        });
    }
    @Test
    public void parseEnvelope_should_be_ok(){
        //GIVEN
        String envelope="0.0 : 30.0|2524.0 : 35.0|8000.0 : 35.0";
        //WHEN
        Map<Double,Double> map=csvService.parseEnvelope(envelope);
        //THEN
        assertFalse(map.isEmpty());
        assertEquals(3,map.size());
    }
    @Test
    public void parseEnvelope_should_return_empty_map_if_envelope_is_empty(){
        //WHEN
        Map<Double,Double> map=csvService.parseEnvelope("");
        //THEN
        assertTrue(map.isEmpty());
    }
    public String getCsvContent(){
        return "F70;0.0 : 30.0|2524.0 : 35.0|35000.0 : 35.0;0.0 : -69.0|35000.0 : -15.7;0.0 : 30.0|2524.0 : 35.0|8000.0 : 35.0;0.0 : -69.0|8000.0 : -56.9\n";
    }

}