package ru.yandex.practicum.collector.model;

public class DeviceAddedEvent extends HubEvent {

    private String id;
    private String deviceType;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
}