package ru.yandex.practicum.collector.model;

public class TemperatureSensorEvent extends SensorEvent {

    private int temperatureC;
    private int temperatureF;

    public int getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(int temperatureC) {
        this.temperatureC = temperatureC;
    }

    public int getTemperatureF() {
        return temperatureF;
    }

    public void setTemperatureF(int temperatureF) {
        this.temperatureF = temperatureF;
    }
}