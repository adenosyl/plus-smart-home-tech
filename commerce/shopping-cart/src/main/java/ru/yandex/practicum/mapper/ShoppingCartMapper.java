package ru.yandex.practicum.mapper;

import ru.yandex.practicum.cart.dto.CartItemDto;
import ru.yandex.practicum.cart.dto.ShoppingCartDto;
import ru.yandex.practicum.entity.CartItem;
import ru.yandex.practicum.entity.ShoppingCart;

public class ShoppingCartMapper {

    public static ShoppingCartDto toDto(ShoppingCart cart) {

        ShoppingCartDto dto = new ShoppingCartDto();

        dto.setCartId(cart.getCartId());
        dto.setUsername(cart.getUsername());
        dto.setState(cart.getState());

        dto.setItems(
                cart.getItems()
                        .stream()
                        .map(item -> {
                            CartItemDto itemDto = new CartItemDto();

                            itemDto.setProductId(item.getProductId());
                            itemDto.setQuantity(item.getQuantity());

                            return itemDto;
                        })
                        .toList()
        );

        return dto;
    }
}