package com.example.aircraftenvlimitations.validation;

import com.example.aircraftenvlimitations.exception.BusinessException;
import com.example.aircraftenvlimitations.exception.ControlType;
import com.example.aircraftenvlimitations.exception.Level;
import com.example.aircraftenvlimitations.exception.MessageList;
import com.example.aircraftenvlimitations.model.AeroPhase;
import com.example.aircraftenvlimitations.utils.ValidationUtils;
import org.springframework.stereotype.Component;


@Component
public class TemperatureDeviationValidator {

    private final String REQUEST_AIRCRAFT_EMPTY="request.aircraft.empty";
    private final String REQUEST_ALTITUDE_NOT_VALID="request.altitude.not.valid";
    private final String REQUEST_AEROPHASE_REQUIRED="request.aerophase.required";

    public void validate(String airCraft, double altitude, AeroPhase aeroPhase) throws BusinessException {
        MessageList messageList=new MessageList();

        ValidationUtils.notEmpty(airCraft,REQUEST_AIRCRAFT_EMPTY,"AIRCRAFT_MODEL", ControlType.FORM_VALIDATION, Level.ERROR,messageList);
        ValidationUtils.isTrue(altitude>0,REQUEST_ALTITUDE_NOT_VALID,"ALTITUDE", ControlType.FORM_VALIDATION, Level.ERROR,messageList);
        ValidationUtils.notNull(aeroPhase,REQUEST_AEROPHASE_REQUIRED,"AERO_PHASE", ControlType.FORM_VALIDATION, Level.ERROR,messageList);

        if(!messageList.get().isEmpty()) throw new BusinessException(messageList);

    }
}
