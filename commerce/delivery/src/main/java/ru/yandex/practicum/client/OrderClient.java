package ru.yandex.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "order")
public interface OrderClient {

    @PostMapping("/api/v1/order/{orderId}/delivery/success")
    void deliverySuccess(
            @PathVariable UUID orderId
    );

    @PostMapping("/api/v1/order/{orderId}/delivery/failed")
    void deliveryFailed(
            @PathVariable UUID orderId
    );

    @PostMapping("/api/v1/order/{orderId}/on-delivery")
    void onDelivery(
            @PathVariable UUID orderId
    );

    @PostMapping("/api/v1/order/{orderId}/done")
    void done(
            @PathVariable UUID orderId
    );
}