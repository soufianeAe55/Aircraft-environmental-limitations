package com.example.aircraftenvlimitations.validation;

import com.example.aircraftenvlimitations.exception.BusinessException;
import com.example.aircraftenvlimitations.exception.Message;
import com.example.aircraftenvlimitations.model.AeroPhase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureDeviationValidatorTest {

    @InjectMocks
    private TemperatureDeviationValidator validator;

    private final String REQUEST_AIRCRAFT_EMPTY="request.aircraft.empty";
    private final String REQUEST_ALTITUDE_NOT_VALID="request.altitude.not.valid";
    private final String REQUEST_AEROPHASE_REQUIRED="request.aerophase.required";

    @Test
    public void validate_aircraft_model_should_not_be_empty(){
        //WHEN
        BusinessException exception=assertThrows(BusinessException.class,()->{
            validator.validate("",30000.9, AeroPhase.APPROACH);
        });
        //THEN
        Assert.assertEquals(1,exception.getMessageList().size());
        Assert.assertTrue(exists(exception.getMessageList(),REQUEST_AIRCRAFT_EMPTY));
    }
    @Test
    public void validate_altitude_should_be_valid(){
        //WHEN
        BusinessException exception=assertThrows(BusinessException.class,()->{
            validator.validate("F70",0, AeroPhase.APPROACH);
        });
        //THEN
        Assert.assertEquals(1,exception.getMessageList().size());
        Assert.assertTrue(exists(exception.getMessageList(),REQUEST_ALTITUDE_NOT_VALID));
    }

    @Test
    public void validate_aeroPhase_should_be_valid(){
        //WHEN
        BusinessException exception=assertThrows(BusinessException.class,()->{
            validator.validate("F70",40000, null);
        });
        //THEN
        Assert.assertEquals(1,exception.getMessageList().size());
        Assert.assertTrue(exists(exception.getMessageList(),REQUEST_AEROPHASE_REQUIRED));
    }

    private boolean exists(List<Message> msgs, String key) {
        return msgs.stream().anyMatch(item -> item.getKey().equals(key));
    }
}