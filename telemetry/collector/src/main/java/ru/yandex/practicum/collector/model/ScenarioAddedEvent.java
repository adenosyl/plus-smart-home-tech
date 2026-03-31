package ru.yandex.practicum.collector.model;

import java.util.List;

public class ScenarioAddedEvent extends HubEvent {

    private String name;
    private List<Object> conditions;
    private List<Object> actions;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Object> getConditions() { return conditions; }
    public void setConditions(List<Object> conditions) { this.conditions = conditions; }

    public List<Object> getActions() { return actions; }
    public void setActions(List<Object> actions) { this.actions = actions; }
}