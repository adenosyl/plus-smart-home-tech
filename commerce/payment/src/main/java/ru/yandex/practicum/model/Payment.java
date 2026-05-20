package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue
    private UUID paymentId;

    private UUID orderId;

    private BigDecimal productCost;

    private BigDecimal deliveryCost;

    private BigDecimal totalCost;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}