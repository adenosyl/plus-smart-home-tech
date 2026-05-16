package ru.yandex.practicum.cart;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.dto.CartItemDto;
import ru.yandex.practicum.cart.dto.ShoppingCartDto;

import java.util.UUID;

@RequestMapping("/api/v1/cart")
public interface ShoppingCartOperations {

    @PostMapping("/{username}")
    ShoppingCartDto create(
            @PathVariable String username
    );

    @GetMapping("/{username}")
    ShoppingCartDto getCart(
            @PathVariable String username
    );

    @PostMapping("/{username}/add")
    ShoppingCartDto addProduct(
            @PathVariable String username,
            @RequestBody CartItemDto dto
    );

    @PatchMapping("/{username}/deactivate")
    ShoppingCartDto deactivate(
            @PathVariable String username
    );

    @PatchMapping("/{username}/quantity")
    ShoppingCartDto updateQuantity(
            @PathVariable String username,
            @RequestBody CartItemDto dto
    );

    @DeleteMapping("/{username}/product/{productId}")
    ShoppingCartDto removeProduct(
            @PathVariable String username,
            @PathVariable UUID productId
    );

    @DeleteMapping("/{username}/clear")
    ShoppingCartDto clear(
            @PathVariable String username
    );
}