package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.entity.Product;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.ProductCategory;
import ru.yandex.practicum.model.ProductState;
import ru.yandex.practicum.model.QuantityState;
import ru.yandex.practicum.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public ProductDto create(ProductDto dto) {

        Product product = ProductMapper.fromDto(dto);

        product.setProductState(ProductState.ACTIVE);

        return ProductMapper.toDto(
                repository.save(product)
        );
    }

    public ProductDto getById(UUID id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return ProductMapper.toDto(product);
    }

    public List<ProductDto> getAll() {

        return repository.findAllByProductState(ProductState.ACTIVE)
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public List<ProductDto> getByCategory(ProductCategory category) {

        return repository.findAllByProductCategoryAndProductState(
                        category,
                        ProductState.ACTIVE
                )
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public ProductDto update(ProductDto dto) {

        Product existing = repository.findById(dto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        existing.setProductName(dto.getProductName());
        existing.setDescription(dto.getDescription());
        existing.setImageSrc(dto.getImageSrc());
        existing.setPrice(dto.getPrice());
        existing.setProductCategory(dto.getProductCategory());
        existing.setQuantityState(dto.getQuantityState());

        return ProductMapper.toDto(
                repository.save(existing)
        );
    }

    public void delete(UUID id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setProductState(ProductState.DEACTIVATE);

        repository.save(product);
    }

    public ProductDto updateQuantityState(UUID id,
                                          QuantityState quantityState) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setQuantityState(quantityState);

        return ProductMapper.toDto(
                repository.save(product)
        );
    }
}