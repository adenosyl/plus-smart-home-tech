package ru.yandex.practicum.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "order_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderBooking {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID orderId;

    private UUID deliveryId;

    @Enumerated(EnumType.STRING)
    private BookingState state;
}