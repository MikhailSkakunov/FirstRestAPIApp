package ru.alishev.springcourse.FirstRestApp.dto;

import javax.validation.constraints.NotNull;

public class MeasurementDTO {

    @NotNull
    private float airTemperature;

    @NotNull
    private boolean conditionsRaining;

    @NotNull
    private SensorDTO sensor;


    public float getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(float airTemperature) {
        this.airTemperature = airTemperature;
    }

    public boolean isConditionsRaining() {
        return conditionsRaining;
    }

    public void setConditionsRaining(boolean conditionsRaining) {
        this.conditionsRaining = conditionsRaining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
