package ru.yandex.practicum.aggregator.serializer;

import org.apache.kafka.common.serialization.Serializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.io.*;
import org.apache.avro.specific.*;

public class SnapshotSerializer implements Serializer<SensorsSnapshotAvro> {

    @Override
    public byte[] serialize(String topic, SensorsSnapshotAvro data) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            DatumWriter<SensorsSnapshotAvro> writer =
                    new SpecificDatumWriter<>(SensorsSnapshotAvro.class);

            BinaryEncoder encoder =
                    EncoderFactory.get().binaryEncoder(out, null);

            writer.write(data, encoder);
            encoder.flush();

            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка сериализации snapshot", e);
        }
    }
}