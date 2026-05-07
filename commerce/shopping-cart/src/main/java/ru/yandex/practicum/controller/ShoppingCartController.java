package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.cart.ShoppingCartOperations;
import ru.yandex.practicum.cart.dto.CartItemDto;
import ru.yandex.practicum.cart.dto.ShoppingCartDto;
import ru.yandex.practicum.service.ShoppingCartService;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ShoppingCartController
        implements ShoppingCartOperations {

    private final ShoppingCartService service;

    @Override
    public ShoppingCartDto create(String username) {
        return service.create(username);
    }

    @Override
    public ShoppingCartDto getByUsername(String username) {
        return service.getByUsername(username);
    }

    @Override
    public ShoppingCartDto addProduct(
            String username,
            CartItemDto dto
    ) {
        return service.addProduct(username, dto);
    }

    @Override
    public ShoppingCartDto deactivate(String username) {
        return service.deactivate(username);
    }

    @Override
    public ShoppingCartDto updateQuantity(
            String username,
            CartItemDto dto
    ) {
        return service.updateQuantity(username, dto);
    }

    @Override
    public ShoppingCartDto removeProduct(
            String username,
            UUID productId
    ) {
        return service.removeProduct(username, productId);
    }

    @Override
    public ShoppingCartDto clear(String username) {
        return service.clear(username);
    }
}