package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.payment.PaymentOperations;
import ru.yandex.practicum.service.PaymentService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController implements PaymentOperations {

    private final PaymentService service;

    @Override
    public PaymentDto create(
            PaymentDto dto
    ) {
        return service.create(dto);
    }

    @Override
    public PaymentDto getById(
            UUID id
    ) {
        return service.getById(id);
    }

    @Override
    public List<PaymentDto> getAll() {
        return service.getAll();
    }

    @Override
    public PaymentDto paymentSuccess(
            UUID paymentId
    ) {
        return service.paymentSuccess(paymentId);
    }

    @Override
    public PaymentDto paymentFailed(
            UUID paymentId
    ) {
        return service.paymentFailed(paymentId);
    }

    @Override
    public BigDecimal calculateProductCost(
            UUID productId,
            Long quantity
    ) {
        return service.productCost(
                productId,
                quantity
        );
    }

    @Override
    public BigDecimal calculateTotalCost(
            BigDecimal productPrice,
            BigDecimal deliveryPrice
    ) {
        return service.calculateTotalCost(
                productPrice,
                deliveryPrice
        );
    }
}