package ru.yandex.practicum.warehouse.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class WarehouseItemDto {

    private UUID productId;

    private Long quantity;

    private Double width;

    private Double height;

    private Double depth;

    private Double weight;

    private Boolean fragile;
}