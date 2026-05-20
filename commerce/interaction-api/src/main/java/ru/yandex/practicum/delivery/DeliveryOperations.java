package ru.yandex.practicum.delivery;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.DeliveryDto;

import java.math.BigDecimal;
import java.util.UUID;

@RequestMapping("/api/v1/delivery")
public interface DeliveryOperations {

    @PostMapping
    DeliveryDto create(
            @RequestBody DeliveryDto dto
    );

    @GetMapping("/{id}")
    DeliveryDto getById(
            @PathVariable UUID id
    );

    @PostMapping("/{deliveryId}/pickup")
    DeliveryDto pickup(
            @PathVariable UUID deliveryId
    );

    @PostMapping("/{deliveryId}/success")
    DeliveryDto deliverySuccess(
            @PathVariable UUID deliveryId
    );

    @PostMapping("/{deliveryId}/failed")
    DeliveryDto deliveryFailed(
            @PathVariable UUID deliveryId
    );

    @PostMapping("/cost")
    BigDecimal calculateCost(
            @RequestBody DeliveryDto dto
    );
}