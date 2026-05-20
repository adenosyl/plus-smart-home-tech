package ru.yandex.practicum.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.cart.dto.CartItemDto;
import ru.yandex.practicum.cart.dto.ShoppingCartDto;
import ru.yandex.practicum.client.WarehouseClient;
import ru.yandex.practicum.entity.CartItem;
import ru.yandex.practicum.entity.ShoppingCart;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.exception.WarehouseUnavailableException;
import ru.yandex.practicum.mapper.ShoppingCartMapper;
import ru.yandex.practicum.model.CartState;
import ru.yandex.practicum.repository.ShoppingCartRepository;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.dto.ShoppingCartItemDto;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository repository;
    private final WarehouseClient warehouseClient;

    private ShoppingCartItemDto toWarehouseItem(
            CartItemDto dto
    ) {

        ShoppingCartItemDto item =
                new ShoppingCartItemDto();

        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());

        return item;
    }

    public ShoppingCartDto create(String username) {

        ShoppingCart cart = new ShoppingCart();

        cart.setUsername(username);
        cart.setState(CartState.ACTIVE);

        return ShoppingCartMapper.toDto(
                repository.save(cart)
        );
    }

    public ShoppingCartDto getCart(String username) {

        ShoppingCart cart = repository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException("Cart not found"));

        return ShoppingCartMapper.toDto(cart);
    }

    @CircuitBreaker(
            name = "warehouse",
            fallbackMethod = "warehouseFallback"
    )
    public ShoppingCartDto addProduct(
            String username,
            CartItemDto dto
    ) {

        ShoppingCart cart = repository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException("Cart not found"));

        if (cart.getState() == CartState.DEACTIVATED) {
            throw new RuntimeException(
                    "Cart is deactivated"
            );
        }

        BookedProductsDto response =
                warehouseClient.checkAvailability(
                        List.of(toWarehouseItem(dto))
                );

        if (!response.getUnavailableProducts().isEmpty()) {

            throw new WarehouseUnavailableException(
                    "Product unavailable in warehouse"
            );
        }

        CartItem existing = cart.getItems()
                .stream()
                .filter(item ->
                        item.getProductId()
                                .equals(dto.getProductId()))
                .findFirst()
                .orElse(null);

        if (existing != null) {

            existing.setQuantity(
                    existing.getQuantity()
                            + dto.getQuantity()
            );

        } else {

            CartItem item = new CartItem();

            item.setProductId(dto.getProductId());
            item.setQuantity(dto.getQuantity());

            cart.getItems().add(item);
        }

        return ShoppingCartMapper.toDto(
                repository.save(cart)
        );
    }

    public ShoppingCartDto deactivate(
            String username
    ) {

        ShoppingCart cart = repository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException("Cart not found"));

        cart.setState(CartState.DEACTIVATED);

        return ShoppingCartMapper.toDto(
                repository.save(cart)
        );
    }

    public ShoppingCartDto updateQuantity(
            String username,
            CartItemDto dto
    ) {

        ShoppingCart cart = repository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException("Cart not found"));

        CartItem item = cart.getItems()
                .stream()
                .filter(i ->
                        i.getProductId()
                                .equals(dto.getProductId()))
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException(
                                "Product not found"
                        ));

        item.setQuantity(dto.getQuantity());

        return ShoppingCartMapper.toDto(
                repository.save(cart)
        );
    }

    public ShoppingCartDto removeProduct(
            String username,
            UUID productId
    ) {

        ShoppingCart cart = repository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException("Cart not found"));

        cart.getItems().removeIf(item ->
                item.getProductId().equals(productId));

        return ShoppingCartMapper.toDto(
                repository.save(cart)
        );
    }

    public ShoppingCartDto clear(
            String username
    ) {

        ShoppingCart cart = repository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException("Cart not found"));

        cart.getItems().clear();

        return ShoppingCartMapper.toDto(
                repository.save(cart)
        );
    }

    public ShoppingCartDto warehouseFallback(
            String username,
            CartItemDto dto,
            Throwable throwable
    ) {

        throw new WarehouseUnavailableException(
                "Warehouse service temporarily unavailable"
        );
    }
}