package ru.yandex.practicum.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    private String country;

    private String city;

    private String street;

    private String house;

    private String flat;
}