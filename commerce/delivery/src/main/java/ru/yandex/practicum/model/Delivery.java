package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue
    private UUID deliveryId;

    private UUID orderId;

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

    private BigDecimal deliveryCost;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}