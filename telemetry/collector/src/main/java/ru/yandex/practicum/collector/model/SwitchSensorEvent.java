package ru.yandex.practicum.collector.model;

public class SwitchSensorEvent extends SensorEvent {

    private boolean state;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}