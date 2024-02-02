package com.example.testypie.domain.product.dto;

import com.example.testypie.domain.product.entity.Product;
import java.time.LocalDateTime;

public record ProductCreateResponseDTO(
        Long id,
        String title,
        String content,
        Long productLikeCnt,
        String category,
        LocalDateTime createAt,
        LocalDateTime startAt,
        LocalDateTime closedAt) {

    public static ProductCreateResponseDTO of(Product product) {

        return new ProductCreateResponseDTO(
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
