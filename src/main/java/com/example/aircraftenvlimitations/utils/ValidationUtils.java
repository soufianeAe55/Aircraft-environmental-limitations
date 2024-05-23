package com.example.aircraftenvlimitations.utils;

import com.example.aircraftenvlimitations.exception.ControlType;
import com.example.aircraftenvlimitations.exception.Level;
import com.example.aircraftenvlimitations.exception.Message;
import com.example.aircraftenvlimitations.exception.MessageList;
import org.apache.commons.lang.StringUtils;


/**
 * Validation utilities for the requests
 */
public interface ValidationUtils {

    static void notEmpty(String value, String key, String filedName, ControlType controlType, Level level , MessageList list){
        value=StringUtils.defaultString(value,"");
        if(value.isBlank()) list.add(new Message(key, filedName, controlType, level));
    }
    static void notNull(Object value,String key, String filedName, ControlType controlType,Level level , MessageList list){
        if(value==null) list.add(new Message(key, filedName, controlType, level));
    }
    static void isTrue(Boolean value, String key, String filedName, ControlType controlType, Level level , MessageList list){
        if(Boolean.FALSE.equals(value)) list.add(new Message(key, filedName, controlType, level));
    }
}
