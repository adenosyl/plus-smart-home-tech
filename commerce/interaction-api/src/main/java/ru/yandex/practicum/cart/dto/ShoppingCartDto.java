package ru.yandex.practicum.cart.dto;

import lombok.Data;
import ru.yandex.practicum.model.CartState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ShoppingCartDto {

    private UUID cartId;

    private String username;

    private CartState state;

    private List<CartItemDto> items = new ArrayList<>();
}