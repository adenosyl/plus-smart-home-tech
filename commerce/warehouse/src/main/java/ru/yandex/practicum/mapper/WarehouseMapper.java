package ru.yandex.practicum.mapper;

import ru.yandex.practicum.entity.WarehouseItem;
import ru.yandex.practicum.warehouse.dto.WarehouseItemDto;

public class WarehouseMapper {

    public static WarehouseItemDto toDto(WarehouseItem item) {

        WarehouseItemDto dto = new WarehouseItemDto();

        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setWidth(item.getWidth());
        dto.setHeight(item.getHeight());
        dto.setDepth(item.getDepth());
        dto.setWeight(item.getWeight());
        dto.setFragile(item.getFragile());

        return dto;
    }

    public static WarehouseItem fromDto(WarehouseItemDto dto) {

        WarehouseItem item = new WarehouseItem();

        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setWidth(dto.getWidth());
        item.setHeight(dto.getHeight());
        item.setDepth(dto.getDepth());
        item.setWeight(dto.getWeight());
        item.setFragile(dto.getFragile());

        return item;
    }
}