package ru.yandex.practicum.analyzer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.analyzer.model.*;
import ru.yandex.practicum.analyzer.repository.ScenarioRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final ActionExecutor actionExecutor;

    @Transactional
    public void processSnapshot(Snapshot snapshot) {

        log.info("📥 Snapshot received: hub={}, values={}", snapshot.getHubId(), snapshot);

        List<Scenario> scenarios =
                scenarioRepository.findByHubId(snapshot.getHubId());

        log.info("Found {} scenarios for hub {}", scenarios.size(), snapshot.getHubId());

        for (Scenario scenario : scenarios) {

            boolean match = scenario.getConditions().stream()
                    .allMatch(condition -> check(condition, snapshot));

            if (match) {
                log.info("🔥 Scenario triggered: {}", scenario.getName());
                executeActions(scenario);
            }
        }
    }

    private boolean check(Condition condition, Snapshot snapshot) {

        Integer sensorValue = snapshot.getValue(condition.getType());
        if (sensorValue == null) return false;

        return switch (condition.getOperation()) {
            case "GREATER_THAN" -> sensorValue > condition.getValue();
            case "LOWER_THAN" -> sensorValue < condition.getValue();
            case "EQUALS" -> sensorValue.equals(condition.getValue());
            default -> false;
        };
    }

    private void executeActions(Scenario scenario) {
        for (Action action : scenario.getActions()) {
            actionExecutor.send(action, scenario.getHubId(), scenario.getName());
        }
    }
}