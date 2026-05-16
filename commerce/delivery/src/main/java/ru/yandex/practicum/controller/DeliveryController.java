package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.delivery.DeliveryOperations;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.service.DeliveryService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController implements DeliveryOperations {

    private final DeliveryService service;

    @Override
    public DeliveryDto create(
            DeliveryDto dto
    ) {
        return service.create(dto);
    }

    @Override
    public DeliveryDto getById(
            UUID id
    ) {
        return service.getById(id);
    }

    @Override
    public DeliveryDto pickup(
            UUID deliveryId
    ) {
        return service.pickup(deliveryId);
    }

    @Override
    public DeliveryDto deliverySuccess(
            UUID deliveryId
    ) {
        return service.deliverySuccess(deliveryId);
    }

    @Override
    public DeliveryDto deliveryFailed(
            UUID deliveryId
    ) {
        return service.deliveryFailed(deliveryId);
    }

    @Override
    public BigDecimal calculateCost(
            DeliveryDto dto
    ) {
        return service.calculateCost(dto);
    }
}