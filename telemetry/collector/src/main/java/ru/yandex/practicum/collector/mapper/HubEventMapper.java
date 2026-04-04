package ru.yandex.practicum.collector.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Component
public class HubEventMapper {

    public HubEventAvro toAvro(HubEvent event) {

        HubEventAvro avro = new HubEventAvro();
        avro.setHubId(event.getHubId());
        avro.setTimestamp(event.getTimestamp().toEpochMilli());

        avro.setPayload(mapPayload(event));

        return avro;
    }

    private Object mapPayload(HubEvent event) {

        if (event instanceof DeviceAddedEvent e) {
            DeviceAddedAvro avro = new DeviceAddedAvro();
            avro.setId(e.getId());
            avro.setDeviceType(e.getDeviceType());
            return avro;
        }

        if (event instanceof DeviceRemovedEvent e) {
            DeviceRemovedAvro avro = new DeviceRemovedAvro();
            avro.setId(e.getId());
            return avro;
        }

        if (event instanceof ScenarioRemovedEvent e) {
            ScenarioRemovedAvro avro = new ScenarioRemovedAvro();
            avro.setName(e.getName());
            return avro;
        }

        if (event instanceof ScenarioAddedEvent e) {
            ScenarioAddedAvro avro = new ScenarioAddedAvro();
            avro.setName(e.getName());
            return avro;
        }

        throw new IllegalArgumentException("Unknown hub event type: " + event.getClass());
    }
}