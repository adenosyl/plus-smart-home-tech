package ru.yandex.practicum.collector.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private Map<String, Object> baseConfig() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                ru.yandex.practicum.collector.serializer.AvroSerializer.class);

        return config;
    }

    @Bean
    public KafkaTemplate<String, SensorEventAvro> sensorKafkaTemplate() {
        ProducerFactory<String, SensorEventAvro> factory =
                new DefaultKafkaProducerFactory<>(baseConfig());

        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaTemplate<String, HubEventAvro> hubKafkaTemplate() {
        ProducerFactory<String, HubEventAvro> factory =
                new DefaultKafkaProducerFactory<>(baseConfig());

        return new KafkaTemplate<>(factory);
    }
}