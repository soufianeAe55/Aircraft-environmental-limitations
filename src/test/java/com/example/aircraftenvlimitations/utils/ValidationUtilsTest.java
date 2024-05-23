package com.example.aircraftenvlimitations.utils;

import com.example.aircraftenvlimitations.exception.ControlType;
import com.example.aircraftenvlimitations.exception.Level;
import com.example.aircraftenvlimitations.exception.MessageList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ValidationUtilsTest {

    @Test
    public void notEmpty_should_add_error_message_when_value_is_empty() {
        //GIVEN
        MessageList messageList = new MessageList();
        //WHEN
        ValidationUtils.notEmpty("","empty.msg", "field",ControlType.FORM_VALIDATION, Level.ERROR, messageList);
        //THEN
        assertEquals(1,messageList.get().size());
        assertEquals("empty.msg",messageList.get().get(0).getKey());
    }

    @Test
    public void notNull_should_add_error_message_when_value_is_null() {
        //GIVEN
        MessageList messageList = new MessageList();
        //WHEN
        ValidationUtils.notNull(null,"null.msg", "field", ControlType.FORM_VALIDATION, Level.ERROR, messageList);
        //THEN
        assertEquals(1,messageList.get().size());
        assertEquals("null.msg",messageList.get().get(0).getKey());
    }

    @Test
    public void isTrue_should_add_error_message_when_value_is_false() {
        //GIVEN
        MessageList messageList = new MessageList();
        //WHEN
        ValidationUtils.isTrue(false,"test.msg", "field",ControlType.FORM_VALIDATION, Level.ERROR, messageList);
        //THEN
        assertEquals(1,messageList.get().size());
        assertEquals("test.msg",messageList.get().get(0).getKey());
    }


    @Test
    public void isTrue_should_do_nothing_when_value_is_true() {
        //GIVEN
        MessageList messageList = new MessageList();
        //WHEN
        ValidationUtils.isTrue(null,"", "field",ControlType.FORM_VALIDATION, Level.ERROR, messageList);
        //THEN
        assertEquals(0,messageList.get().size());
    }

}