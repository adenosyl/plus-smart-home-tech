package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.order.OrderOperations;
import ru.yandex.practicum.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController implements OrderOperations {

    private final OrderService service;

    @Override
    public OrderDto create(
            OrderDto dto
    ) {
        return service.create(dto);
    }

    @Override
    public OrderDto getById(
            UUID id
    ) {
        return service.getById(id);
    }

    @Override
    public List<OrderDto> getAll() {
        return service.getAll();
    }

    @Override
    public OrderDto paymentSuccess(
            UUID id
    ) {
        return service.paymentSuccess(id);
    }

    @Override
    public OrderDto paymentFailed(
            UUID id
    ) {
        return service.paymentFailed(id);
    }

    @Override
    public OrderDto deliverySuccess(
            UUID id
    ) {
        return service.deliverySuccess(id);
    }

    @Override
    public OrderDto deliveryFailed(
            UUID id
    ) {
        return service.deliveryFailed(id);
    }

    @Override
    public OrderDto assembled(
            UUID id
    ) {
        return service.assembled(id);
    }

    @Override
    public OrderDto onDelivery(
            UUID id
    ) {
        return service.onDelivery(id);
    }

    @Override
    public OrderDto done(
            UUID id
    ) {
        return service.done(id);
    }

    @Override
    public OrderDto returnOrder(
            UUID id
    ) {
        return service.returnOrder(id);
    }

    @Override
    public OrderDto cancel(
            UUID id
    ) {
        return service.cancel(id);
    }

    @Override
    public OrderDto paid(
            UUID id
    ) {
        return service.paid(id);
    }

    @Override
    public OrderDto onPayment(
            UUID id
    ) {
        return service.onPayment(id);
    }

    @Override
    public OrderDto completed(
            UUID id
    ) {
        return service.completed(id);
    }

    @Override
    public OrderDto assemblyFailed(
            UUID orderId
    ) {
        return service.assemblyFailed(orderId);
    }
}