package ru.yandex.practicum.warehouse.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookedProductsDto {

    private List<UUID> unavailableProducts;
}