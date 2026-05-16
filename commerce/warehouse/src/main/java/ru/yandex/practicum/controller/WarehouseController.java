package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.service.WarehouseService;
import ru.yandex.practicum.warehouse.WarehouseOperations;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.dto.ShoppingCartItemDto;
import ru.yandex.practicum.warehouse.dto.WarehouseItemDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController
        implements WarehouseOperations {

    private final WarehouseService service;

    @Override
    public WarehouseItemDto add(
            WarehouseItemDto dto
    ) {
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

    @Override
    public void assembly(
            AssemblyProductsForOrderRequest request
    ) {
        service.assemblyProductForOrderFromShoppingCart(
                request
        );
    }

    @Override
    public void shippedToDelivery(
            UUID orderId,
            UUID deliveryId
    ) {
        service.shippedToDelivery(
                orderId,
                deliveryId
        );
    }

    @Override
    public void returnProducts(
            Map<UUID, Long> products
    ) {
        service.returnProducts(products);
    }
}