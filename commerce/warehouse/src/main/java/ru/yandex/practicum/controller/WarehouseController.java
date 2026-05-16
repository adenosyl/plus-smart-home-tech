package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.service.WarehouseService;
import ru.yandex.practicum.warehouse.WarehouseOperations;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.dto.ShoppingCartItemDto;
import ru.yandex.practicum.warehouse.dto.WarehouseItemDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WarehouseController implements WarehouseOperations {

    private final WarehouseService service;

    @Override
    public WarehouseItemDto add(WarehouseItemDto dto) {
        return service.add(dto);
    }

    @Override
    public BookedProductsDto checkAvailability(
            List<ShoppingCartItemDto> items
    ) {
        return service.checkAvailability(items);
    }

    @Override
    public AddressDto getAddress() {
        return service.getAddress();
    }
}