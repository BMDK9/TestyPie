package com.example.testypie.domain.product.dto.response;

import com.example.testypie.domain.product.entity.Product;
import java.time.LocalDateTime;

public record UpdateProductResponseDTO(
        Long id,
        String title,
        String content,
        Long productLikeCnt,
        String category,
        LocalDateTime modifiedAt,
        LocalDateTime startAt,
        LocalDateTime closedAt) {
    public static UpdateProductResponseDTO of(Product product) {
        return new UpdateProductResponseDTO(
                product.getId(),
                product.getTitle(),
                product.getContent(),
                product.getProductLikeCnt(),
                product.getCategory().getName(),
                product.getModifiedAt(),
                product.getStartedAt(),
                product.getClosedAt());
    }
}
