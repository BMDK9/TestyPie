package com.example.testypie.domain.product.dto.response;

import com.example.testypie.domain.product.entity.Product;

public record DeleteProductResponseDTO(Long id) {
    public static DeleteProductResponseDTO of(Product product) {
        return new DeleteProductResponseDTO(product.getId());
    }
}
