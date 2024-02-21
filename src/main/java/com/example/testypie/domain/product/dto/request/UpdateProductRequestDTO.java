package com.example.testypie.domain.product.dto.request;

public record UpdateProductRequestDTO(
        String title, String content, String startAt, String closedAt) {}
