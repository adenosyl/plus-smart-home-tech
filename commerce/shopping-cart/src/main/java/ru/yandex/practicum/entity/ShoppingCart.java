package ru.yandex.practicum.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.yandex.practicum.model.CartState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {

    @Id
    @GeneratedValue
    private UUID cartId;

    private String username;

    @Enumerated(EnumType.STRING)
    private CartState state;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItem> items = new ArrayList<>();
}