package ru.yandex.practicum.collector.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;

@Service
public class HubEventProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public HubEventProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(HubEventAvro event) {
        kafkaTemplate.send(Topics.HUBS, event.getHubId(), serialize(event));
    }

    private byte[] serialize(Object avro) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);

            SpecificDatumWriter writer = new SpecificDatumWriter(avro.getClass());

            writer.write(avro, encoder);
            encoder.flush();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}