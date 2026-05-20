package ru.yandex.practicum.order;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.OrderDto;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/order")
public interface OrderOperations {

    @PostMapping
    OrderDto create(
            @RequestBody OrderDto dto
    );

    @GetMapping("/{orderId}")
    OrderDto getById(
            @PathVariable("orderId") UUID orderId
    );

    @GetMapping
    List<OrderDto> getAll();

    @PostMapping("/{orderId}/payment/success")
    OrderDto paymentSuccess(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/payment/failed")
    OrderDto paymentFailed(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/delivery/success")
    OrderDto deliverySuccess(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/delivery/failed")
    OrderDto deliveryFailed(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/assembled")
    OrderDto assembled(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/on-delivery")
    OrderDto onDelivery(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/done")
    OrderDto done(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/return")
    OrderDto returnOrder(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/cancel")
    OrderDto cancel(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/paid")
    OrderDto paid(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/on-payment")
    OrderDto onPayment(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/completed")
    OrderDto completed(
            @PathVariable("orderId") UUID orderId
    );

    @PostMapping("/{orderId}/assembly/failed")
    OrderDto assemblyFailed(
            @PathVariable("orderId") UUID orderId
    );
}