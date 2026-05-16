package ru.yandex.practicum.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.entity.BookingState;
import ru.yandex.practicum.entity.OrderBooking;
import ru.yandex.practicum.entity.WarehouseItem;
import ru.yandex.practicum.mapper.WarehouseMapper;
import ru.yandex.practicum.repository.OrderBookingRepository;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.warehouse.dto.*;
import ru.yandex.practicum.dto.AssemblyProductsForOrderRequest;

import java.security.SecureRandom;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository repository;

    private final OrderBookingRepository orderBookingRepository;

    private static final String[] ADDRESSES =
            new String[]{"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS =
            ADDRESSES[
                    Random.from(new SecureRandom())
                            .nextInt(0, ADDRESSES.length)
                    ];

    public WarehouseItemDto add(WarehouseItemDto dto) {

        WarehouseItem item = WarehouseMapper.fromDto(dto);

        return WarehouseMapper.toDto(
                repository.save(item)
        );
    }

    public BookedProductsDto checkAvailability(
            List<ShoppingCartItemDto> items
    ) {

        List<UUID> unavailable = new ArrayList<>();

        for (ShoppingCartItemDto cartItem : items) {

            WarehouseItem item = repository.findByProductId(
                    cartItem.getProductId()
            ).orElse(null);

            if (item == null ||
                    item.getQuantity() < cartItem.getQuantity()) {

                unavailable.add(cartItem.getProductId());
            }
        }

        BookedProductsDto dto = new BookedProductsDto();
        dto.setUnavailableProducts(unavailable);

        return dto;
    }

    public AddressDto getAddress() {

        return new AddressDto(
                CURRENT_ADDRESS,
                CURRENT_ADDRESS,
                CURRENT_ADDRESS,
                CURRENT_ADDRESS,
                CURRENT_ADDRESS
        );
    }

    @Transactional
    public void assemblyProductForOrderFromShoppingCart(
            AssemblyProductsForOrderRequest request
    ) {

        Map<UUID, Long> products = request.getProducts();

        // Проверка остатков
        for (Map.Entry<UUID, Long> entry : products.entrySet()) {

            WarehouseItem item = repository
                    .findByProductId(entry.getKey())
                    .orElseThrow();

            if (item.getQuantity() < entry.getValue()) {
                throw new RuntimeException(
                        "Недостаточно товара на складе"
                );
            }
        }

        // Уменьшение остатков
        for (Map.Entry<UUID, Long> entry : products.entrySet()) {

            WarehouseItem item = repository
                    .findByProductId(entry.getKey())
                    .orElseThrow();

            item.setQuantity(
                    item.getQuantity() - entry.getValue()
            );

            repository.save(item);
        }

        // Создание booking
        OrderBooking booking = OrderBooking.builder()
                .orderId(request.getOrderId())
                .state(BookingState.ASSEMBLED)
                .build();

        orderBookingRepository.save(booking);
    }

    @Transactional
    public void shippedToDelivery(
            UUID orderId,
            UUID deliveryId
    ) {

        OrderBooking booking = orderBookingRepository
                .findByOrderId(orderId)
                .orElse(null);

        if (booking == null) {

            booking = OrderBooking.builder()
                    .orderId(orderId)
                    .deliveryId(deliveryId)
                    .state(BookingState.SHIPPED)
                    .build();

        } else {

            booking.setDeliveryId(deliveryId);

            booking.setState(BookingState.SHIPPED);
        }

        orderBookingRepository.save(booking);
    }

    @Transactional
    public void returnProducts(
            Map<UUID, Long> products
    ) {

        for (Map.Entry<UUID, Long> entry : products.entrySet()) {

            WarehouseItem item = repository
                    .findByProductId(entry.getKey())
                    .orElseThrow();

            item.setQuantity(
                    item.getQuantity() + entry.getValue()
            );

            repository.save(item);
        }
    }
}