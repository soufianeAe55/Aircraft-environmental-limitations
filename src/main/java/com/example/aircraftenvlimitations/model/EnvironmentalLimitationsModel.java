package com.example.aircraftenvlimitations.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentalLimitationsModel {
    private String airCraftModel;
    private Map<Double, Double> maxTemperature;
    private Map<Double, Double> minTemperature;
}
