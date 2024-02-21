package com.example.testypie.domain.category.dto.response;

import com.example.testypie.domain.category.entity.Category;

public record CreateCategoryResponseDTO(Long id, String name, Long depth, Category parent) {

    public static CreateCategoryResponseDTO of(Category category) {
        return new CreateCategoryResponseDTO(
                category.getId(), category.getName(), category.getDepth(), category.getParent());
    }
}
