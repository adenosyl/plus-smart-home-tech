package ru.yandex.practicum.collector.serializer;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AvroSerializer<T> implements Serializer<T> {

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null) return null;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            SpecificDatumWriter<T> writer = new SpecificDatumWriter<>((Class<T>) data.getClass());

            writer.write(data, encoder);
            encoder.flush();

            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка сериализации Avro", e);
        }
    }
}