package ru.yandex.practicum.cart.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CartItemDto {

    private UUID productId;

    private Integer quantity;
}