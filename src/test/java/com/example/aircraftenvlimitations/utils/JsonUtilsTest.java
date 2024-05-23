package com.example.aircraftenvlimitations.utils;


import com.example.aircraftenvlimitations.dto.MessageError;
import com.example.aircraftenvlimitations.exception.ControlType;
import com.example.aircraftenvlimitations.exception.Level;
import com.example.aircraftenvlimitations.exception.TechnicalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilsTest {

    @Test
    public void asJsonString() {
        //GIVEN
        JsonUtils.setMapper(new ObjectMapper());
        //WHEN
        String result = JsonUtils.asJsonString(new MessageError("Message", "field", ControlType.FORM_VALIDATION, Level.ERROR));
        //THEN
        assertEquals("{\"filedName\":\"field\",\"controlType\":\"FORM_VALIDATION\",\"level\":\"ERROR\",\"message\":\"Message\"}", result);
    }
    @Test(expected = TechnicalException.class)
    public void asJsonString_should_throw_technical_exception() throws JsonProcessingException {
        //GIVEN
        ObjectMapper mapper= Mockito.mock(ObjectMapper.class);
        JsonUtils.setMapper(mapper);
        //WHEN
        MessageError msg=new MessageError("Message", "field", ControlType.FORM_VALIDATION, Level.ERROR);
        when(mapper.writeValueAsString(msg)).thenThrow(new RuntimeException());
        //THEN
        JsonUtils.asJsonString(msg);
      }

}