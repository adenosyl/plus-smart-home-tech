package ru.yandex.practicum.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID productId;

    private Integer quantity;
}