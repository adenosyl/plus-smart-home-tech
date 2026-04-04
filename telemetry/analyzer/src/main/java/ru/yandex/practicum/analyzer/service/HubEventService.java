package ru.yandex.practicum.analyzer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.analyzer.model.Sensor;
import ru.yandex.practicum.analyzer.repository.*;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.analyzer.model.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HubEventService {

    private final ScenarioRepository scenarioRepository;
    private final SensorRepository sensorRepository;

    public void process(HubEventProto event) {

        switch (event.getPayloadCase()) {

            case SCENARIO_ADDED:
                handleScenarioAdded(event.getScenarioAdded(), event.getHubId());
                break;

            case SCENARIO_REMOVED:
                handleScenarioRemoved(event.getScenarioRemoved(), event.getHubId());
                break;

            case DEVICE_ADDED:
                handleDeviceAdded(event.getDeviceAdded(), event.getHubId());
                break;

            case DEVICE_REMOVED:
                handleDeviceRemoved(event.getDeviceRemoved());
                break;

            default:
                break;
        }
    }

    // ✅ правильный тип
    private void handleScenarioAdded(ScenarioAddedEventProto event, String hubId) {

        Scenario scenario = new Scenario();
        scenario.setHubId(hubId);
        scenario.setName(event.getName());

        List<Condition> conditions = event.getConditionList().stream()
                .map(proto -> {
                    Condition condition = new Condition();
                    condition.setSensorId(proto.getSensorId());
                    condition.setType(proto.getType().name());
                    condition.setOperation(proto.getOperation().name());

                    if (proto.hasIntValue()) {
                        condition.setValue(proto.getIntValue());
                    } else if (proto.hasBoolValue()) {
                        condition.setValue(proto.getBoolValue() ? 1 : 0);
                    }

                    return condition;
                })
                .toList();

        List<Action> actions = event.getActionList().stream()
                .map(proto -> {
                    Action action = new Action();
                    action.setSensorId(proto.getSensorId());
                    action.setType(proto.getType().name());

                    if (proto.hasValue()) {
                        action.setValue(proto.getValue());
                    }

                    return action;
                })
                .toList();

        scenario.setConditions(conditions);
        scenario.setActions(actions);

        scenarioRepository.save(scenario);
    }

    private void handleScenarioRemoved(ScenarioRemovedEventProto event, String hubId) {
        scenarioRepository
                .findByHubIdAndName(hubId, event.getName())
                .ifPresent(scenarioRepository::delete);
    }

    private void handleDeviceAdded(DeviceAddedEventProto event, String hubId) {

        Sensor sensor = new Sensor();
        sensor.setId(event.getId());
        sensor.setHubId(hubId);

        sensorRepository.save(sensor);
    }

    private void handleDeviceRemoved(DeviceRemovedEventProto event) {
        sensorRepository.deleteById(event.getId());
    }
}