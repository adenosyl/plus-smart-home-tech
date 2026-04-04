package ru.yandex.practicum.analyzer.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.model.Snapshot;
import ru.yandex.practicum.analyzer.service.ScenarioService;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import jakarta.annotation.PostConstruct;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class SnapshotProcessor {

    private final KafkaConsumer<String, byte[]> consumer;
    private final ScenarioService scenarioService;

    public SnapshotProcessor(
            @Qualifier("snapshotConsumer") KafkaConsumer<String, byte[]> consumer,
            ScenarioService scenarioService
    ) {
        this.consumer = consumer;
        this.scenarioService = scenarioService;
    }

    public void start() {

        consumer.subscribe(List.of("telemetry.snapshots.v1"));

        log.info("SnapshotProcessor started");

        while (true) {
            ConsumerRecords<String, byte[]> records =
                    consumer.poll(Duration.ofSeconds(1));

            for (ConsumerRecord<String, byte[]> record : records) {

                try {
                    SensorEventProto event =
                            SensorEventProto.parseFrom(record.value());

                    Snapshot snapshot = map(event);

                    scenarioService.processSnapshot(snapshot);

                } catch (Exception e) {
                    log.error("Snapshot processing error", e);
                }
            }

            consumer.commitSync();
        }
    }

    @PostConstruct
    public void init() {
        new Thread(this::start).start();
    }

    private Snapshot map(SensorEventProto event) {

        Snapshot snapshot = new Snapshot();
        snapshot.setHubId(event.getHubId());

        switch (event.getPayloadCase()) {

            case MOTION_SENSOR:
                snapshot.addSensorValue(
                        "MOTION",
                        event.getMotionSensor().getMotion() ? 1 : 0
                );
                break;

            case TEMPERATURE_SENSOR:
                snapshot.addSensorValue(
                        "TEMPERATURE",
                        event.getTemperatureSensor().getTemperatureC()
                );
                break;

            case LIGHT_SENSOR:
                snapshot.addSensorValue(
                        "LUMINOSITY",
                        event.getLightSensor().getLuminosity()
                );
                break;

            case CLIMATE_SENSOR:
                snapshot.addSensorValue(
                        "TEMPERATURE",
                        event.getClimateSensor().getTemperatureC()
                );
                snapshot.addSensorValue(
                        "HUMIDITY",
                        event.getClimateSensor().getHumidity()
                );
                snapshot.addSensorValue(
                        "CO2LEVEL",
                        event.getClimateSensor().getCo2Level()
                );
                break;

            case SWITCH_SENSOR:
                snapshot.addSensorValue(
                        "SWITCH",
                        event.getSwitchSensor().getState() ? 1 : 0
                );
                break;

            default:
                break;
        }

        return snapshot;
    }
}