package ru.yandex.practicum.collector.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
@RequiredArgsConstructor
public class SensorEventProducer {

    private final KafkaTemplate<String, SensorEventAvro> kafkaTemplate;

    public void send(SensorEventAvro event) {
        kafkaTemplate.send("telemetry.sensors.v1", event.getHubId(), event);
    }
}