package ru.yandex.practicum.collector.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.collector.mapper.SensorEventMapper;
import ru.yandex.practicum.collector.model.SensorEvent;
import ru.yandex.practicum.collector.producer.SensorEventProducer;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@RestController
@RequestMapping("/events/sensors")
public class SensorController {

    private final SensorEventMapper mapper;
    private final SensorEventProducer producer;

    public SensorController(SensorEventMapper mapper, SensorEventProducer producer) {
        this.mapper = mapper;
        this.producer = producer;
    }

    @PostMapping
    public void collect(@RequestBody SensorEvent event) {
        SensorEventAvro avro = mapper.toAvro(event);
        producer.send(avro);
    }
}