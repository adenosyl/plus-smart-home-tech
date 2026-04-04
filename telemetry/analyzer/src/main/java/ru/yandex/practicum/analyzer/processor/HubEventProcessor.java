package ru.yandex.practicum.analyzer.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.service.HubEventService;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import jakarta.annotation.PostConstruct;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class HubEventProcessor implements Runnable {

    private final KafkaConsumer<String, byte[]> consumer;
    private final HubEventService hubEventService;

    public HubEventProcessor(
            @Qualifier("hubEventConsumer") KafkaConsumer<String, byte[]> consumer,
            HubEventService hubEventService
    ) {
        this.consumer = consumer;
        this.hubEventService = hubEventService;
    }

    @PostConstruct
    public void init() {
        new Thread(this).start();
    }

    @Override
    public void run() {

        consumer.subscribe(List.of("telemetry.hubs.v1"));

        log.info("HubEventProcessor started");

        while (true) {

            ConsumerRecords<String, byte[]> records =
                    consumer.poll(Duration.ofSeconds(1));

            for (ConsumerRecord<String, byte[]> record : records) {

                try {
                    HubEventProto event =
                            HubEventProto.parseFrom(record.value());

                    hubEventService.process(event);

                } catch (Exception e) {
                    log.error("Hub event error", e);
                }
            }

            consumer.commitSync();
        }
    }
}