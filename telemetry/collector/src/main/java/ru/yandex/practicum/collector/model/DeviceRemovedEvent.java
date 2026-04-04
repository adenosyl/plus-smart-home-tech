package ru.yandex.practicum.collector.model;

public class DeviceRemovedEvent extends HubEvent {

    private String id;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}