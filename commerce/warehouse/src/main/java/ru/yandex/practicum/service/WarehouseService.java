package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.entity.WarehouseItem;
import ru.yandex.practicum.mapper.WarehouseMapper;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.warehouse.dto.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository repository;

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

        List<java.util.UUID> unavailable = new ArrayList<>();

        for (ShoppingCartItemDto cartItem : items) {

            WarehouseItem item = repository.findById(
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
}