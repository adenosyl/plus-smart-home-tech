package ru.yandex.practicum.aggregator.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SnapshotService {

    private final Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    public Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event) {

        SensorsSnapshotAvro snapshot =
                snapshots.computeIfAbsent(event.getHubId(), hub -> {
                    SensorsSnapshotAvro s = new SensorsSnapshotAvro();
                    s.setHubId(hub);
                    s.setSensorsState(new HashMap<>());
                    return s;
                });

        SensorStateAvro oldState =
                snapshot.getSensorsState().get(event.getId());

        if (oldState != null) {

            if (oldState.getTimestamp() > event.getTimestamp()) {
                return Optional.empty();
            }

            if (oldState.getData() != null &&
                    oldState.getData().toString().equals(event.getPayload().toString())) {
                return Optional.empty();
            }
        }

        SensorStateAvro newState = new SensorStateAvro();
        newState.setTimestamp(event.getTimestamp());
        newState.setData(event.getPayload().toString());

        snapshot.getSensorsState().put(event.getId(), newState);
        snapshot.setTimestamp(event.getTimestamp());

        return Optional.of(snapshot);
    }
}