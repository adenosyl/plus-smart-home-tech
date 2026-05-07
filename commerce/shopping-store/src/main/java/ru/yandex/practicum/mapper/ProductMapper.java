package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.entity.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product) {

        ProductDto dto = new ProductDto();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setImageSrc(product.getImageSrc());
        dto.setPrice(product.getPrice());
        dto.setProductCategory(product.getProductCategory());
        dto.setProductState(product.getProductState());
        dto.setQuantityState(product.getQuantityState());

        return dto;
    }

    public static Product fromDto(ProductDto dto) {

        Product product = new Product();

        product.setProductId(dto.getProductId());
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setImageSrc(dto.getImageSrc());
        product.setPrice(dto.getPrice());
        product.setProductCategory(dto.getProductCategory());
        product.setProductState(dto.getProductState());
        product.setQuantityState(dto.getQuantityState());

        return product;
    }
}