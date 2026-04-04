package ru.yandex.practicum.collector.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Component
public class SensorEventMapper {

    public SensorEventAvro toAvro(SensorEvent event) {

        SensorEventAvro avro = new SensorEventAvro();

        avro.setId(event.getId());
        avro.setHubId(event.getHubId());
        avro.setTimestamp(event.getTimestamp().toEpochMilli());

        avro.setPayload(mapPayload(event));

        return avro;
    }

    private Object mapPayload(SensorEvent event) {

        if (event instanceof LightSensorEvent e) {
            LightSensorAvro avro = new LightSensorAvro();
            avro.setLinkQuality(e.getLinkQuality());
            avro.setLuminosity(e.getLuminosity());
            return avro;
        }

        if (event instanceof MotionSensorEvent e) {
            MotionSensorAvro avro = new MotionSensorAvro();
            avro.setLinkQuality(e.getLinkQuality());
            avro.setMotion(e.isMotion());
            avro.setVoltage(e.getVoltage());
            return avro;
        }

        if (event instanceof SwitchSensorEvent e) {
            SwitchSensorAvro avro = new SwitchSensorAvro();
            avro.setState(e.isState());
            return avro;
        }

        if (event instanceof ClimateSensorEvent e) {
            ClimateSensorAvro avro = new ClimateSensorAvro();
            avro.setTemperatureC(e.getTemperatureC());
            avro.setHumidity(e.getHumidity());
            avro.setCo2Level(e.getCo2Level());
            return avro;
        }

        if (event instanceof TemperatureSensorEvent e) {
            TemperatureSensorAvro avro = new TemperatureSensorAvro();
            avro.setId(e.getId());
            avro.setHubId(e.getHubId());
            avro.setTimestamp(e.getTimestamp().toEpochMilli());
            avro.setTemperatureC(e.getTemperatureC());
            avro.setTemperatureF(e.getTemperatureF());
            return avro;
        }

        throw new IllegalArgumentException("Unknown sensor event type: " + event.getClass());
    }
}