package ru.yandex.practicum.collector.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.collector.mapper.HubEventMapper;
import ru.yandex.practicum.collector.model.HubEvent;
import ru.yandex.practicum.collector.producer.HubEventProducer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@RestController
@RequestMapping("/events/hubs")
public class HubController {

    private final HubEventMapper mapper;
    private final HubEventProducer producer;

    public HubController(HubEventMapper mapper, HubEventProducer producer) {
        this.mapper = mapper;
        this.producer = producer;
    }

    @PostMapping
    public void collect(@RequestBody HubEvent event) {
        HubEventAvro avro = mapper.toAvro(event);
        producer.send(avro);
    }
}