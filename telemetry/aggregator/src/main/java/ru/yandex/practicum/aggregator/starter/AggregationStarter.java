package ru.yandex.practicum.aggregator.starter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.apache.kafka.common.errors.WakeupException;

import ru.yandex.practicum.aggregator.service.SnapshotService;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;


import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private final KafkaConsumer<String, SensorEventAvro> consumer;
    private final KafkaProducer<String, SensorsSnapshotAvro> producer;
    private final SnapshotService snapshotService;

    public void start() {

        consumer.subscribe(List.of("telemetry.sensors.v1"));

        try {
            while (true) {

                ConsumerRecords<String, SensorEventAvro> records =
                        consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, SensorEventAvro> record : records) {

                    Optional<SensorsSnapshotAvro> updated =
                            snapshotService.updateState(record.value());

                    updated.ifPresent(snapshot -> {
                        producer.send(new ProducerRecord<>(
                                "telemetry.snapshots.v1",
                                snapshot.getHubId(),
                                snapshot
                        ));
                        log.info("Snapshot отправлен для hub={}", snapshot.getHubId());
                    });
                }

                consumer.commitSync();
            }

        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("Ошибка обработки событий", e);

        } finally {
            try {
                producer.flush();
                consumer.commitSync();
            } finally {
                log.info("Закрытие consumer и producer");
                consumer.close();
                producer.close();
            }
        }
    }
}