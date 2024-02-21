package com.example.testypie.domain.productLike.dto.response;

public record ProductLikeResponseDto(Boolean isProductLiked) {

    public static ProductLikeResponseDto of(Boolean isProductLiked) {

        return new ProductLikeResponseDto(isProductLiked);
    }
}
