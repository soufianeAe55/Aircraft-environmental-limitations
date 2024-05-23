package com.example.aircraftenvlimitations.api;

import com.example.aircraftenvlimitations.business.EnvironmentalLimitationsService;
import com.example.aircraftenvlimitations.dto.TemperatureDeviationResponse;
import com.example.aircraftenvlimitations.model.AeroPhase;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EnvironmentalLimitationsControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EnvironmentalLimitationsController controller;
    @Mock
    private EnvironmentalLimitationsService service;

    private final  String AIRCRAFT_PARAM="airCraft";
    private final  String ALTITUDE_PARAM="altitude";
    private final  String AEROPHASE_PARAM="aeroPhase";

    @Before
    public void Setup(){
        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void test_getTemperatureDeviation() throws Exception {
        //GIVEN
        TemperatureDeviationResponse response=TemperatureDeviationResponse.builder()
                .maxTemperature(29d)
                .minTemperature(10d)
                .build();
        //WHEN
        doReturn(response).when(service).getTemperatureDeviation(any(),anyDouble(),any());
        ResultActions result= mockMvc.perform(get("/performance/temperatureDeviation")
                        .param(AIRCRAFT_PARAM,"B772LR")
                        .param(ALTITUDE_PARAM,"30000")
                        .param(AEROPHASE_PARAM, AeroPhase.CRUISE.get())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"));
        MvcResult mvcResult=result.andExpect(status().is(HttpStatus.SC_OK)).andDo(print()).andReturn();
        //THEN
        Assert.assertEquals("{\"maxTemperature\":29.0,\"minTemperature\":10.0}",
                mvcResult.getResponse().getContentAsString());
    }




}