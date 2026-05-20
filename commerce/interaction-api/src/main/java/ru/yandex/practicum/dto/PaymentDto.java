package ru.yandex.practicum.dto;

import lombok.*;
import ru.yandex.practicum.model.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private UUID paymentId;

    private UUID orderId;

    private BigDecimal productCost;

    private BigDecimal deliveryCost;

    private BigDecimal totalCost;

    private PaymentStatus status;
}