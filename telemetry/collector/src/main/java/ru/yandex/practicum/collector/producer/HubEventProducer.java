package ru.yandex.practicum.collector.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
@RequiredArgsConstructor
public class HubEventProducer {

    private final KafkaTemplate<String, HubEventAvro> kafkaTemplate;

    public void send(HubEventAvro event) {
        kafkaTemplate.send("telemetry.hubs.v1", event.getHubId(), event);
    }
}