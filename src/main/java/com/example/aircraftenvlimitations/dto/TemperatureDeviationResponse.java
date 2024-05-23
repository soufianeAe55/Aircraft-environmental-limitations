package com.example.aircraftenvlimitations.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemperatureDeviationResponse {
    private  Double maxTemperature;
    private Double minTemperature;
}
