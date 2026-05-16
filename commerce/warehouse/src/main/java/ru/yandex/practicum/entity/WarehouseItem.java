package ru.yandex.practicum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "warehouse_items")
public class WarehouseItem {

    @Id
    private UUID productId;

    private Integer quantity;

    private Double width;

    private Double height;

    private Double depth;

    private Double weight;

    private Boolean fragile;
}