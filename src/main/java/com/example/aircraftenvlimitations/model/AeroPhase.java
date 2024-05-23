package com.example.aircraftenvlimitations.model;

public enum AeroPhase {

    CRUISE("CRUISE"),
    INITIAL_CLIMB("INITIAL_CLIMB"),
    TAKEOFF("TAKEOFF"),
    APPROACH("APPROACH"),
    LANDING("LANDING");

    private String type;

    AeroPhase(String type){
        this.type=type;
    }
    public String get(){
        return this.type;
    }
}
