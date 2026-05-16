package ru.yandex.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.dto.ShoppingCartItemDto;
import ru.yandex.practicum.warehouse.dto.WarehouseItemDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "warehouse")
public interface WarehouseClient {

    @PostMapping("/api/v1/warehouse")
    WarehouseItemDto add(
            @RequestBody WarehouseItemDto dto
    );

    @PostMapping("/api/v1/warehouse/check")
    BookedProductsDto checkAvailability(
            @RequestBody List<ShoppingCartItemDto> items
    );

    @GetMapping("/api/v1/warehouse/address")
    AddressDto getAddress();

    @PostMapping("/api/v1/warehouse/assembly")
    void assembly(
            @RequestBody AssemblyProductsForOrderRequest request
    );

    @PostMapping("/api/v1/warehouse/shipped")
    void shipped(
            @RequestParam UUID orderId,
            @RequestParam UUID deliveryId
    );

    @PostMapping("/api/v1/warehouse/return")
    void returnProducts(
            @RequestBody Map<UUID, Long> products
    );
}