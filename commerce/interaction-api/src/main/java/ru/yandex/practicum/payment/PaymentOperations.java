package ru.yandex.practicum.payment;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.PaymentDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/payment")
public interface PaymentOperations {

    @PostMapping
    PaymentDto create(
            @RequestBody PaymentDto dto
    );

    @GetMapping("/{id}")
    PaymentDto getById(
            @PathVariable UUID id
    );

    @GetMapping
    List<PaymentDto> getAll();

    @PostMapping("/{paymentId}/success")
    PaymentDto paymentSuccess(
            @PathVariable UUID paymentId
    );

    @PostMapping("/{paymentId}/failed")
    PaymentDto paymentFailed(
            @PathVariable UUID paymentId
    );

    @GetMapping("/product-cost")
    BigDecimal calculateProductCost(
            @RequestParam UUID productId,
            @RequestParam Long quantity
    );

    @GetMapping("/total-cost")
    BigDecimal calculateTotalCost(
            @RequestParam BigDecimal productPrice,
            @RequestParam BigDecimal deliveryPrice
    );
}