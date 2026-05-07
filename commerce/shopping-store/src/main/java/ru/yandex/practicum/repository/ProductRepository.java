package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.entity.Product;
import ru.yandex.practicum.model.ProductCategory;
import ru.yandex.practicum.model.ProductState;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByProductState(ProductState state);

    List<Product> findAllByProductCategoryAndProductState(
            ProductCategory category,
            ProductState state
    );
}