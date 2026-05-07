package ru.yandex.practicum.collector.mapper;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Component
public class HubEventMapper {

    public HubEventAvro toAvro(HubEventProto proto) {

        HubEventAvro avro = new HubEventAvro();

        avro.setHubId(proto.getHubId());
        avro.setTimestamp(proto.getTimestamp().getSeconds());

        avro.setPayload(mapPayload(proto));

        return avro;
    }

    private Object mapPayload(HubEventProto proto) {

        switch (proto.getPayloadCase()) {

            case DEVICE_ADDED -> {
                DeviceAddedAvro avro = new DeviceAddedAvro();

                avro.setId(proto.getDeviceAdded().getId());

                avro.setDeviceType(proto.getDeviceAdded().getType().name());

                return avro;
            }

            case DEVICE_REMOVED -> {
                DeviceRemovedAvro avro = new DeviceRemovedAvro();
                avro.setId(proto.getDeviceRemoved().getId());
                return avro;
            }

            case SCENARIO_ADDED -> {
                ScenarioAddedAvro avro = new ScenarioAddedAvro();
                avro.setName(proto.getScenarioAdded().getName());
                return avro;
            }

            case SCENARIO_REMOVED -> {
                ScenarioRemovedAvro avro = new ScenarioRemovedAvro();
                avro.setName(proto.getScenarioRemoved().getName());
                return avro;
            }

            default -> throw new IllegalArgumentException("Unknown hub event: " + proto.getPayloadCase());
        }
    }
}