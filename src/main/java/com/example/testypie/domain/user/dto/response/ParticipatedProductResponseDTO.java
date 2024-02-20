package com.example.testypie.domain.user.dto.response;

import com.example.testypie.domain.product.entity.Product;
import java.time.LocalDateTime;

public record ParticipatedProductResponseDTO(String title, LocalDateTime createdAt) {
    public static ParticipatedProductResponseDTO of(Product product) {
        return new ParticipatedProductResponseDTO(product.getTitle(), product.getCreatedAt());
    }
}
