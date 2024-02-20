package com.example.testypie.domain.user.dto;

import com.example.testypie.domain.product.entity.Product;
import java.time.LocalDateTime;

public record RegisteredProductResponseDTO(
        Long id, String title, LocalDateTime createdAt, LocalDateTime closedAt) {
    public static RegisteredProductResponseDTO of(Product product) {
        return new RegisteredProductResponseDTO(
                product.getId(), product.getTitle(), product.getCreatedAt(), product.getClosedAt());
    }
}
