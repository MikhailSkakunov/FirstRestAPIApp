package ru.alishev.springcourse.FirstRestApp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SensorDTO {
    @NotNull
    @Min(value = 3)
    @Max(value = 30)
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
