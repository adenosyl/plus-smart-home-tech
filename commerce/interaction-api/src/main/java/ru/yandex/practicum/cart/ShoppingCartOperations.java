package ru.yandex.practicum.cart;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.dto.CartItemDto;
import ru.yandex.practicum.cart.dto.ShoppingCartDto;

import java.util.UUID;

public interface ShoppingCartOperations {

    @PostMapping("/api/v1/cart/{username}")
    ShoppingCartDto create(
            @PathVariable String username
    );

    @GetMapping("/api/v1/cart/{username}")
    ShoppingCartDto getByUsername(
            @PathVariable String username
    );

    @PostMapping("/api/v1/cart/{username}/add")
    ShoppingCartDto addProduct(
            @PathVariable String username,
            @RequestBody CartItemDto dto
    );

    @PatchMapping("/api/v1/cart/{username}/deactivate")
    ShoppingCartDto deactivate(
            @PathVariable String username
    );

    @PatchMapping("/api/v1/cart/{username}/quantity")
    ShoppingCartDto updateQuantity(
            @PathVariable String username,
            @RequestBody CartItemDto dto
    );

    @DeleteMapping("/api/v1/cart/{username}/product/{productId}")
    ShoppingCartDto removeProduct(
            @PathVariable String username,
            @PathVariable UUID productId
    );

    @DeleteMapping("/api/v1/cart/{username}/clear")
    ShoppingCartDto clear(
            @PathVariable String username
    );
}