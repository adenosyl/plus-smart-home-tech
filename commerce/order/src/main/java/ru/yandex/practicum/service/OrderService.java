package ru.yandex.practicum.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.client.DeliveryClient;
import ru.yandex.practicum.client.PaymentClient;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.model.OrderState;
import ru.yandex.practicum.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final PaymentClient paymentClient;
    private final DeliveryClient deliveryClient;

    @CircuitBreaker(
            name = "orderService",
            fallbackMethod = "fallbackCreateOrder"
    )
    public OrderDto create(OrderDto dto) {

        Order order = Order.builder()
                .state(OrderState.NEW)
                .shoppingCartId(dto.getShoppingCartId())
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

                .productPrice(dto.getProductPrice())
                .build();

        Order savedOrder = repository.save(order);

        PaymentDto paymentRequest = PaymentDto.builder()
                .orderId(savedOrder.getOrderId())
                .productCost(dto.getProductPrice())
                .deliveryCost(dto.getDeliveryPrice())
                .build();

        PaymentDto payment =
                paymentClient.create(paymentRequest);

        DeliveryDto deliveryRequest = DeliveryDto.builder()
                .orderId(savedOrder.getOrderId())
                .weight(dto.getWeight())
                .volume(dto.getVolume())
                .fragile(dto.getFragile())
                .fromAddress(dto.getFromAddress())
                .toAddress(dto.getToAddress())
                .build();

        DeliveryDto delivery =
                deliveryClient.create(deliveryRequest);

        savedOrder.setPaymentId(
                payment.getPaymentId()
        );

        savedOrder.setDeliveryId(
                delivery.getDeliveryId()
        );

        savedOrder.setDeliveryPrice(
                delivery.getDeliveryCost()
        );

        savedOrder.setTotalPrice(
                payment.getTotalCost()
        );

        savedOrder.setState(
                OrderState.ON_PAYMENT
        );

        Order updatedOrder =
                repository.save(savedOrder);

        return mapToDto(updatedOrder);
    }

    public OrderDto fallbackCreateOrder(
            OrderDto dto,
            Throwable throwable
    ) {

        throwable.printStackTrace();

        throw new RuntimeException(
                throwable.getMessage()
        );
    }

    public OrderDto getById(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        return mapToDto(order);
    }

    public List<OrderDto> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private OrderDto mapToDto(Order order) {

        return OrderDto.builder()
                .orderId(order.getOrderId())
                .state(order.getState())
                .shoppingCartId(order.getShoppingCartId())
                .deliveryId(order.getDeliveryId())
                .paymentId(order.getPaymentId())
                .weight(order.getWeight())
                .volume(order.getVolume())
                .fragile(order.getFragile())

                .fromAddress(
                        AddressDto.builder()
                                .country(order.getFromAddress().getCountry())
                                .city(order.getFromAddress().getCity())
                                .street(order.getFromAddress().getStreet())
                                .house(order.getFromAddress().getHouse())
                                .flat(order.getFromAddress().getFlat())
                                .build()
                )

                .toAddress(
                        AddressDto.builder()
                                .country(order.getToAddress().getCountry())
                                .city(order.getToAddress().getCity())
                                .street(order.getToAddress().getStreet())
                                .house(order.getToAddress().getHouse())
                                .flat(order.getToAddress().getFlat())
                                .build()
                )

                .totalPrice(order.getTotalPrice())
                .productPrice(order.getProductPrice())
                .deliveryPrice(order.getDeliveryPrice())
                .build();
    }

    public OrderDto paymentSuccess(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(OrderState.PAID);

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto paymentFailed(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(OrderState.PAYMENT_FAILED);

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto deliverySuccess(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(OrderState.DONE);

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto deliveryFailed(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(OrderState.DELIVERY_FAILED);

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto paid(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(OrderState.PAID);

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto assembled(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(OrderState.ASSEMBLED);

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto onDelivery(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(OrderState.ON_DELIVERY);

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto done(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(OrderState.DONE);

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto returnOrder(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(
                OrderState.PRODUCT_RETURNED
        );

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto cancel(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(
                OrderState.CANCELED
        );

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto onPayment(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(OrderState.ON_PAYMENT);

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto completed(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(OrderState.COMPLETED);

        return mapToDto(
                repository.save(order)
        );
    }

    public OrderDto assemblyFailed(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow();

        order.setState(OrderState.ASSEMBLY_FAILED);

        return mapToDto(
                repository.save(order)
        );
    }
}