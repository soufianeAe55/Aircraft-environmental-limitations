package com.example.aircraftenvlimitations.business;


import com.example.aircraftenvlimitations.exception.BusinessException;
import com.example.aircraftenvlimitations.exception.TechnicalException;
import com.example.aircraftenvlimitations.model.AeroPhase;
import com.example.aircraftenvlimitations.model.EnvironmentalLimitationsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "environmental.limitations.file")
public class CsvService {

    private final Logger logger = LoggerFactory.getLogger(CsvService.class);

    private final ResourceLoader resourceLoader;

    @Value("${environmental.limitations.file}")
    private String csvFile;

    final private String AIRCRAFT_NOT_FOUND="aircraft.not.found";

    public CsvService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public EnvironmentalLimitationsModel loadDataByAirCraftAndAeroPhase(String airCraft, AeroPhase aeroPhase) throws BusinessException {
        logger.debug("Retrieving the dataset from the csv file...");
        EnvironmentalLimitationsModel model= new EnvironmentalLimitationsModel();
        try(BufferedReader br=new BufferedReader(new InputStreamReader(resourceLoader.getResource("classpath:"+csvFile).getInputStream()))){
            String line;
            while((line= br.readLine())!=null){
                String[] columns=line.split(";",-1);
                if(airCraft.equals(columns[0])){
                    model.setAirCraftModel(airCraft);
                    switch (aeroPhase){
                        case CRUISE, APPROACH, INITIAL_CLIMB -> {
                            model.setMaxTemperature(parseEnvelope(columns[1]));
                            model.setMinTemperature(parseEnvelope(columns[2]));
                        }
                        case TAKEOFF, LANDING -> {
                            model.setMaxTemperature(parseEnvelope(columns[3]));
                            model.setMinTemperature(parseEnvelope(columns[4]));
                        }
                    }
                    break;
                }
            }
            if(model.getAirCraftModel()==null) throw new BusinessException(AIRCRAFT_NOT_FOUND);
        } catch (IOException e) {
            throw new TechnicalException(e);
        }
        return model;
    }

    public Map<Double, Double> parseEnvelope(String envelope){
        Map<Double,Double> map=new HashMap<>();
        if(envelope.isEmpty()) return map;
        String[] pairs=envelope.split("\\|");
        for(String pair:pairs){
            String[] keyValue=pair.split(":");
            map.put(Double.valueOf(keyValue[0]),Double.valueOf(keyValue[1]));
        }
        return map;
    }
}
