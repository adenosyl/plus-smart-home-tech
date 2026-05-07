package ru.yandex.practicum.warehouse.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ShoppingCartItemDto {

    private UUID productId;

    private Integer quantity;
}