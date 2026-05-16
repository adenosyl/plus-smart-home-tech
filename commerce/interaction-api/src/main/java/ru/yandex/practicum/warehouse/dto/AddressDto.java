package ru.yandex.practicum.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDto {

    private String country;

    private String city;

    private String street;

    private String house;

    private String flat;
}