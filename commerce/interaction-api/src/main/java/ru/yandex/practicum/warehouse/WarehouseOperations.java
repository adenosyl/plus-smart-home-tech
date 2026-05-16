package ru.yandex.practicum.warehouse;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.dto.ShoppingCartItemDto;
import ru.yandex.practicum.warehouse.dto.WarehouseItemDto;

import java.util.List;

public interface WarehouseOperations {

    @PostMapping("/api/v1/warehouse")
    WarehouseItemDto add(@RequestBody WarehouseItemDto dto);

    @PostMapping("/api/v1/warehouse/check")
    BookedProductsDto checkAvailability(
            @RequestBody List<ShoppingCartItemDto> items
    );

    @GetMapping("/api/v1/warehouse/address")
    AddressDto getAddress();
}