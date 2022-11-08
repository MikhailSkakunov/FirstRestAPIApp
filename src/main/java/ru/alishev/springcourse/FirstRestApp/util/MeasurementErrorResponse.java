package ru.alishev.springcourse.FirstRestApp.util;

public class MeasurementErrorResponse {

    private String massage;

    private long currentTimeMillis;

    public MeasurementErrorResponse(String massage, long currentTimeMillis) {
        this.massage = massage;
        this.currentTimeMillis = currentTimeMillis;
    }

    public String getMassage() {
        return massage;
    }

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public void setCurrentTimeMillis(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }
}
