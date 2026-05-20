package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    private UUID shoppingCartId;

    private UUID deliveryId;

    private UUID paymentId;

    private Double weight;

    private Double volume;

    private Boolean fragile;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "country",
                    column = @Column(name = "from_country")
            ),
            @AttributeOverride(
                    name = "city",
                    column = @Column(name = "from_city")
            ),
            @AttributeOverride(
                    name = "street",
                    column = @Column(name = "from_street")
            ),
            @AttributeOverride(
                    name = "house",
                    column = @Column(name = "from_house")
            ),
            @AttributeOverride(
                    name = "flat",
                    column = @Column(name = "from_flat")
            )
    })
    private Address fromAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "country",
                    column = @Column(name = "to_country")
            ),
            @AttributeOverride(
                    name = "city",
                    column = @Column(name = "to_city")
            ),
            @AttributeOverride(
                    name = "street",
                    column = @Column(name = "to_street")
            ),
            @AttributeOverride(
                    name = "house",
                    column = @Column(name = "to_house")
            ),
            @AttributeOverride(
                    name = "flat",
                    column = @Column(name = "to_flat")
            )
    })
    private Address toAddress;

    private BigDecimal totalPrice;

    private BigDecimal productPrice;

    private BigDecimal deliveryPrice;
}