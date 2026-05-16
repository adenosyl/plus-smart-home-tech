package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.client.OrderClient;
import ru.yandex.practicum.client.ShoppingStoreClient;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.model.PaymentStatus;
import ru.yandex.practicum.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final OrderClient orderClient;
    private final ShoppingStoreClient shoppingStoreClient;

    public PaymentDto create(PaymentDto dto) {

        Payment payment = Payment.builder()
                .orderId(dto.getOrderId())
                .productCost(dto.getProductCost())
                .deliveryCost(dto.getDeliveryCost())
                .totalCost(calculateTotalCost(
                        dto.getProductCost(),
                        dto.getDeliveryCost()
                ))
                .status(PaymentStatus.PENDING)
                .build();

        Payment saved = repository.save(payment);

        return mapToDto(saved);
    }

    public PaymentDto paymentSuccess(UUID paymentId) {

        Payment payment = repository.findById(paymentId)
                .orElseThrow();

        payment.setStatus(PaymentStatus.SUCCESS);

        Payment saved = repository.save(payment);

        if (saved.getOrderId() != null) {

            orderClient.paymentSuccess(
                    saved.getOrderId()
            );
        }

        return mapToDto(saved);
    }

    public PaymentDto paymentFailed(UUID paymentId) {

        Payment payment = repository.findById(paymentId)
                .orElseThrow();

        payment.setStatus(PaymentStatus.FAILED);

        Payment saved = repository.save(payment);

        if (saved.getOrderId() != null) {

            orderClient.paymentFailed(
                    saved.getOrderId()
            );
        }

        return mapToDto(saved);
    }

    public PaymentDto getById(UUID id) {

        Payment payment = repository.findById(id)
                .orElseThrow();

        return mapToDto(payment);
    }

    public List<PaymentDto> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public BigDecimal productCost(
            UUID productId,
            Long quantity
    ) {

        ProductDto product = shoppingStoreClient
                .getById(productId);

        return BigDecimal.valueOf(product.getPrice())
                .multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal calculateTotalCost(
            BigDecimal productCost,
            BigDecimal deliveryCost
    ) {

        BigDecimal vat =
                productCost.multiply(BigDecimal.valueOf(0.1));

        return productCost
                .add(vat)
                .add(deliveryCost);
    }

    private PaymentDto mapToDto(Payment payment) {

        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .productCost(payment.getProductCost())
                .deliveryCost(payment.getDeliveryCost())
                .totalCost(payment.getTotalCost())
                .status(payment.getStatus())
                .build();
    }
}