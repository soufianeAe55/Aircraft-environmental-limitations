package com.example.aircraftenvlimitations.business;


import com.example.aircraftenvlimitations.mapper.TemperatureDeviationMapper;
import com.example.aircraftenvlimitations.dto.TemperatureDeviationResponse;
import com.example.aircraftenvlimitations.exception.BusinessException;
import com.example.aircraftenvlimitations.model.AeroPhase;
import com.example.aircraftenvlimitations.model.EnvironmentalLimitationsModel;
import com.example.aircraftenvlimitations.utils.AltitudeTemperatureUtil;
import com.example.aircraftenvlimitations.validation.TemperatureDeviationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EnvironmentalLimitationsService {

    private final Logger logger = LoggerFactory.getLogger(EnvironmentalLimitationsService.class);

    private final CsvService csvService;
    private final TemperatureDeviationValidator validator;

    public EnvironmentalLimitationsService(CsvService csvService, TemperatureDeviationValidator validator) {
        this.csvService = csvService;
        this.validator=validator;
    }

    public TemperatureDeviationResponse getTemperatureDeviation(String airCraft, Double altitude, AeroPhase aeroPhase) throws BusinessException {
        logger.debug("Retrieving the temperature deviation of {} ... ", airCraft);
        validator.validate(airCraft,altitude,aeroPhase);
        EnvironmentalLimitationsModel model=csvService.loadDataByAirCraftAndAeroPhase(airCraft,aeroPhase);
        Double maxTemperature=getTemperatureByAltitude(model.getMaxTemperature(),altitude);
        Double minTemperature=getTemperatureByAltitude(model.getMinTemperature(),altitude);
        return TemperatureDeviationMapper.map(maxTemperature,minTemperature);
    }

    public Double getTemperatureByAltitude(Map<Double, Double> temperatureMap, Double altitude ){
        if(temperatureMap== null || temperatureMap.isEmpty()) return null;
        List<Double> altitudes=new ArrayList<>(temperatureMap.keySet());
        Collections.sort(altitudes);
        if(altitude<altitudes.get(0) || altitude>altitudes.get(altitudes.size() - 1))
            return null;
        return AltitudeTemperatureUtil.binarySearch(temperatureMap,altitudes, altitude);
    }


}
