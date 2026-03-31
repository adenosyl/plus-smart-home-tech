package ru.yandex.practicum.collector.model;

public class ScenarioRemovedEvent extends HubEvent {

    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}