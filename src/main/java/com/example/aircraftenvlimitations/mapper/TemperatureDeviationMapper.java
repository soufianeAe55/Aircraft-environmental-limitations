package com.example.aircraftenvlimitations.mapper;

import com.example.aircraftenvlimitations.dto.TemperatureDeviationResponse;

public interface TemperatureDeviationMapper {

    static TemperatureDeviationResponse map(Double max, Double min){
        return TemperatureDeviationResponse.builder()
                .maxTemperature(max)
                .minTemperature(min)
                .build();
    }
}
