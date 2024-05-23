package com.example.aircraftenvlimitations.dto;

import com.example.aircraftenvlimitations.exception.ControlType;
import com.example.aircraftenvlimitations.exception.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MessageError {

    private String Message;
    private String filedName;
    private ControlType controlType;
    private Level level;

}
