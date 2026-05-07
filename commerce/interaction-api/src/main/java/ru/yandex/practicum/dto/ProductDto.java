package ru.yandex.practicum.dto;

import lombok.Data;
import ru.yandex.practicum.model.ProductCategory;
import ru.yandex.practicum.model.ProductState;
import ru.yandex.practicum.model.QuantityState;

import java.util.UUID;

@Data
public class ProductDto {

    private UUID productId;

    private String productName;

    private String description;

    private String imageSrc;

    private Double price;

    private ProductCategory productCategory;

    private ProductState productState;

    private QuantityState quantityState;
}