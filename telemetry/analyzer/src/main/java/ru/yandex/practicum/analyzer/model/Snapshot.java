package ru.yandex.practicum.analyzer.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Snapshot {

    private String hubId;
    private Map<String, Integer> sensors = new HashMap<>();

    public void addSensorValue(String type, int value) {
        sensors.put(type, value);
    }

    public Integer getValue(String type) {
        return sensors.get(type);
    }

    @Override
    public String toString() {
        return sensors.toString();
    }
}