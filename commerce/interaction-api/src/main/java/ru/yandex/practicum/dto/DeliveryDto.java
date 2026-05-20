package ru.yandex.practicum.dto;

import lombok.*;

import ru.yandex.practicum.model.DeliveryStatus;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {

    private UUID deliveryId;

    private UUID orderId;

    private Double weight;

    private Double volume;

    private Boolean fragile;

    private AddressDto fromAddress;

    private AddressDto toAddress;

    private BigDecimal deliveryCost;

    private DeliveryStatus status;
}