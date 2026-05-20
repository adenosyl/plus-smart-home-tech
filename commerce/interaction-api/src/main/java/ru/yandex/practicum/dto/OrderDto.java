package ru.yandex.practicum.dto;

import lombok.*;
import ru.yandex.practicum.model.OrderState;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private UUID orderId;

    private OrderState state;

    private UUID shoppingCartId;

    private UUID deliveryId;

    private UUID paymentId;

    private Double weight;

    private Double volume;

    private Boolean fragile;

    private AddressDto fromAddress;

    private AddressDto toAddress;

    private BigDecimal totalPrice;

    private BigDecimal productPrice;

    private BigDecimal deliveryPrice;
}