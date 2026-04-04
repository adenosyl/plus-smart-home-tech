package ru.yandex.practicum.aggregator.deserializer;

import org.apache.avro.Schema;
import org.apache.avro.io.*;
import org.apache.avro.specific.*;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;

public class BaseAvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {

    private final DecoderFactory decoderFactory;
    private final Schema schema;

    public BaseAvroDeserializer(Schema schema) {
        this(DecoderFactory.get(), schema);
    }

    public BaseAvroDeserializer(DecoderFactory decoderFactory, Schema schema) {
        this.decoderFactory = decoderFactory;
        this.schema = schema;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            SpecificDatumReader<T> reader = new SpecificDatumReader<>(schema);
            BinaryDecoder decoder =
                    decoderFactory.binaryDecoder(new ByteArrayInputStream(data), null);

            return reader.read(null, decoder);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка десериализации", e);
        }
    }
}