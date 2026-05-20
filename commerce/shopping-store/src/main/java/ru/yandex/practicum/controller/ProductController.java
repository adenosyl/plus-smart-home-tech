package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.model.ProductCategory;
import ru.yandex.practicum.model.QuantityState;
import ru.yandex.practicum.service.ProductService;
import ru.yandex.practicum.store.ShoppingStoreOperations;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class ProductController implements ShoppingStoreOperations {

    private final ProductService service;

    @Override
    public ProductDto create(ProductDto dto) {
        return service.create(dto);
    }

    @Override
    public ProductDto getById(UUID id) {
        return service.getById(id);
    }

    @Override
    public List<ProductDto> getAll() {
        return service.getAll();
    }

    @Override
    public List<ProductDto> getByCategory(ProductCategory category) {
        return service.getByCategory(category);
    }

    @Override
    public ProductDto update(ProductDto dto) {
        return service.update(dto);
    }

    @Override
    public void delete(UUID id) {
        service.delete(id);
    }

    @Override
    public ProductDto updateQuantityState(UUID id,
                                          QuantityState quantityState) {

        return service.updateQuantityState(id, quantityState);
    }
}