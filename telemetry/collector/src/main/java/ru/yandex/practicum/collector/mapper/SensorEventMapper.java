package ru.yandex.practicum.collector.mapper;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Component
public class SensorEventMapper {

    public SensorEventAvro toAvro(SensorEventProto proto) {

        SensorEventAvro avro = new SensorEventAvro();

        avro.setId(proto.getId());
        avro.setHubId(proto.getHubId());
        avro.setTimestamp(proto.getTimestamp().getSeconds());

        avro.setPayload(mapPayload(proto));

        return avro;
    }

    private Object mapPayload(SensorEventProto proto) {

        switch (proto.getPayloadCase()) {

            case TEMPERATURE_SENSOR -> {
                TemperatureSensorAvro avro = new TemperatureSensorAvro();

                avro.setId(proto.getId());
                avro.setHubId(proto.getHubId());
                avro.setTimestamp(proto.getTimestamp().getSeconds());

                avro.setTemperatureC(proto.getTemperatureSensor().getTemperatureC());
                avro.setTemperatureF(proto.getTemperatureSensor().getTemperatureF());

                return avro;
            }

            case MOTION_SENSOR -> {
                MotionSensorAvro avro = new MotionSensorAvro();

                avro.setLinkQuality(proto.getMotionSensor().getLinkQuality());
                avro.setMotion(proto.getMotionSensor().getMotion());
                avro.setVoltage(proto.getMotionSensor().getVoltage());

                return avro;
            }

            case LIGHT_SENSOR -> {
                LightSensorAvro avro = new LightSensorAvro();

                avro.setLinkQuality(proto.getLightSensor().getLinkQuality());
                avro.setLuminosity(proto.getLightSensor().getLuminosity());

                return avro;
            }

            case CLIMATE_SENSOR -> {
                ClimateSensorAvro avro = new ClimateSensorAvro();

                avro.setTemperatureC(proto.getClimateSensor().getTemperatureC());
                avro.setHumidity(proto.getClimateSensor().getHumidity());
                avro.setCo2Level(proto.getClimateSensor().getCo2Level());

                return avro;
            }

            case SWITCH_SENSOR -> {
                SwitchSensorAvro avro = new SwitchSensorAvro();

                avro.setState(proto.getSwitchSensor().getState());

                return avro;
            }

            default -> throw new IllegalArgumentException("Unknown payload: " + proto.getPayloadCase());
        }
    }
}