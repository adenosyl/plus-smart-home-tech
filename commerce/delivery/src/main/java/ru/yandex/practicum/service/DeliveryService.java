package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.client.OrderClient;
import ru.yandex.practicum.client.WarehouseClient;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.model.DeliveryStatus;
import ru.yandex.practicum.repository.DeliveryRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository repository;
    private final OrderClient orderClient;
    private final WarehouseClient warehouseClient;

    public DeliveryDto create(DeliveryDto dto) {

        BigDecimal cost = calculateCost(dto);

        Delivery delivery = Delivery.builder()
                .orderId(dto.getOrderId())
                .weight(dto.getWeight())
                .volume(dto.getVolume())
                .fragile(dto.getFragile())

                .fromAddress(
                        Address.builder()
                                .country(dto.getFromAddress().getCountry())
                                .city(dto.getFromAddress().getCity())
                                .street(dto.getFromAddress().getStreet())
                                .house(dto.getFromAddress().getHouse())
                                .flat(dto.getFromAddress().getFlat())
                                .build()
                )

                .toAddress(
                        Address.builder()
                                .country(dto.getToAddress().getCountry())
                                .city(dto.getToAddress().getCity())
                                .street(dto.getToAddress().getStreet())
                                .house(dto.getToAddress().getHouse())
                                .flat(dto.getToAddress().getFlat())
                                .build()
                )

                .deliveryCost(cost)
                .status(DeliveryStatus.CREATED)
                .build();

        Delivery saved = repository.save(delivery);

        return mapToDto(saved);
    }

    public DeliveryDto getById(UUID id) {

        Delivery delivery = repository.findById(id)
                .orElseThrow();

        return mapToDto(delivery);
    }

    public List<DeliveryDto> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public DeliveryDto pickup(UUID deliveryId) {

        Delivery delivery = repository.findById(deliveryId)
                .orElseThrow();

        delivery.setStatus(
                DeliveryStatus.IN_PROGRESS
        );

        Delivery saved =
                repository.save(delivery);

        warehouseClient.shippedToDelivery(
                saved.getOrderId(),
                saved.getDeliveryId()
        );

        if (saved.getOrderId() != null) {

            orderClient.onDelivery(
                    saved.getOrderId()
            );
        }

        return mapToDto(saved);
    }

    public DeliveryDto deliverySuccess(UUID id) {

        Delivery delivery = repository.findById(id)
                .orElseThrow();

        delivery.setStatus(DeliveryStatus.DELIVERED);

        Delivery saved =
                repository.save(delivery);

        if (saved.getOrderId() != null) {

            orderClient.done(
                    saved.getOrderId()
            );
        }

        return mapToDto(saved);
    }

    public DeliveryDto deliveryFailed(UUID id) {

        Delivery delivery = repository.findById(id)
                .orElseThrow();

        delivery.setStatus(DeliveryStatus.FAILED);

        Delivery saved =
                repository.save(delivery);

        if (saved.getOrderId() != null) {

            orderClient.deliveryFailed(
                    saved.getOrderId()
            );
        }

        return mapToDto(saved);
    }

    public BigDecimal calculateCost(
            DeliveryDto dto
    ) {

        double cost = 5.0;

        if (dto.getFromAddress()
                .getStreet()
                .contains("ADDRESS_2")) {

            cost += 5.0 * 2;
        } else {
            cost += 5.0;
        }

        if (Boolean.TRUE.equals(dto.getFragile())) {
            cost += cost * 0.2;
        }

        cost += dto.getWeight() * 0.3;

        cost += dto.getVolume() * 0.2;

        boolean sameStreet =
                dto.getFromAddress()
                        .getStreet()
                        .equalsIgnoreCase(
                                dto.getToAddress()
                                        .getStreet()
                        );

        if (!sameStreet) {
            cost += cost * 0.2;
        }

        return BigDecimal.valueOf(cost);
    }

    private DeliveryDto mapToDto(Delivery delivery) {

        return DeliveryDto.builder()
                .deliveryId(delivery.getDeliveryId())
                .orderId(delivery.getOrderId())
                .weight(delivery.getWeight())
                .volume(delivery.getVolume())
                .fragile(delivery.getFragile())

                .fromAddress(
                        AddressDto.builder()
                                .country(delivery.getFromAddress().getCountry())
                                .city(delivery.getFromAddress().getCity())
                                .street(delivery.getFromAddress().getStreet())
                                .house(delivery.getFromAddress().getHouse())
                                .flat(delivery.getFromAddress().getFlat())
                                .build()
                )

                .toAddress(
                        AddressDto.builder()
                                .country(delivery.getToAddress().getCountry())
                                .city(delivery.getToAddress().getCity())
                                .street(delivery.getToAddress().getStreet())
                                .house(delivery.getToAddress().getHouse())
                                .flat(delivery.getToAddress().getFlat())
                                .build()
                )

                .deliveryCost(delivery.getDeliveryCost())
                .status(delivery.getStatus())
                .build();
    }
}