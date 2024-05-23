package com.example.aircraftenvlimitations.api;

import com.example.aircraftenvlimitations.business.EnvironmentalLimitationsService;
import com.example.aircraftenvlimitations.dto.CommonResponseError;
import com.example.aircraftenvlimitations.dto.TemperatureDeviationResponse;
import com.example.aircraftenvlimitations.exception.BusinessException;
import com.example.aircraftenvlimitations.model.AeroPhase;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/performance")
public class EnvironmentalLimitationsController {

    private final Logger logger = LoggerFactory.getLogger(EnvironmentalLimitationsController.class);

    private final EnvironmentalLimitationsService environmentalLimitationsService;

    public EnvironmentalLimitationsController(EnvironmentalLimitationsService envLimitationsService) {
        this.environmentalLimitationsService = envLimitationsService;
    }

    @ApiOperation(value = "Retrieve the temperature deviation of an aircraft model")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = TemperatureDeviationResponse.class),
            @ApiResponse(code = 400, message = "Validation error", response = CommonResponseError.class),
            @ApiResponse(code = 500, message = "technical error", response = CommonResponseError.class),
    })
    @GetMapping(value="/temperatureDeviation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TemperatureDeviationResponse> getTemperatureDeviation(
            @ApiParam(name = "airCraft", value = "airCraft") @RequestParam(value = "airCraft") String airCraft,
            @ApiParam(name = "altitude", value = "altitude") @RequestParam(value = "altitude") double altitude,
            @ApiParam(name = "aeroPhase", value = "aeroPhase") @RequestParam(value = "aeroPhase") AeroPhase aeroPhase) throws BusinessException {
        logger.debug("Retrieve the temperature deviation of {} at the altitude {} ", airCraft, altitude);
        return new ResponseEntity<>(environmentalLimitationsService.getTemperatureDeviation(airCraft,altitude,aeroPhase), HttpStatus.OK);
    }

}
