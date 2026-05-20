package ru.yandex.practicum.warehouse;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.dto.ShoppingCartItemDto;
import ru.yandex.practicum.warehouse.dto.WarehouseItemDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/v1/warehouse")
public interface WarehouseOperations {

    @PostMapping
    WarehouseItemDto add(
            @RequestBody WarehouseItemDto dto
    );

    @PostMapping("/check")
    BookedProductsDto checkAvailability(
            @RequestBody List<ShoppingCartItemDto> items
    );

    @GetMapping("/address")
    AddressDto getAddress();

    @PostMapping("/assembly")
    void assembly(
            @RequestBody AssemblyProductsForOrderRequest request
    );

    @PostMapping("/shipped")
    void shippedToDelivery(
            @RequestParam UUID orderId,
            @RequestParam UUID deliveryId
    );

    @PostMapping("/return")
    void returnProducts(
            @RequestBody Map<UUID, Long> products
    );
}