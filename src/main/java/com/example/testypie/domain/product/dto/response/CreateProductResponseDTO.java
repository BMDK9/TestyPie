package com.example.testypie.domain.product.dto.response;

import com.example.testypie.domain.product.entity.Product;
import java.time.LocalDateTime;

public record CreateProductResponseDTO(
        Long id,
        String title,
        String content,
        Long productLikeCnt,
        String category,
        LocalDateTime createAt,
        LocalDateTime startAt,
        LocalDateTime closedAt) {

    public static CreateProductResponseDTO of(Product product) {

        return new CreateProductResponseDTO(
                product.getId(),
                product.getTitle(),
                product.getContent(),
                product.getProductLikeCnt(),
                product.getCategory().getName(),
                product.getCreatedAt(),
                product.getStartedAt(),
                product.getClosedAt());
    }
}
