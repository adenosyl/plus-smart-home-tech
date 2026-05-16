package ru.yandex.practicum.store;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.model.ProductCategory;
import ru.yandex.practicum.model.QuantityState;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/store")
public interface ShoppingStoreOperations {

    @PostMapping
    ProductDto create(@RequestBody ProductDto dto);

    @GetMapping("/{id}")
    ProductDto getById(@PathVariable UUID id);

    @GetMapping
    List<ProductDto> getAll();

    @GetMapping("/category/{category}")
    List<ProductDto> getByCategory(
            @PathVariable ProductCategory category
    );

    @PutMapping
    ProductDto update(@RequestBody ProductDto dto);

    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id);

    @PatchMapping("/{id}/quantity")
    ProductDto updateQuantityState(
            @PathVariable UUID id,
            @RequestParam QuantityState quantityState
    );
}