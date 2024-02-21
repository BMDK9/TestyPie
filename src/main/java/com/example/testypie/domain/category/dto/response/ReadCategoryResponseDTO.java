package com.example.testypie.domain.category.dto.response;

import com.example.testypie.domain.category.entity.Category;

public record ReadCategoryResponseDTO(Long id, String name, Long depth, String parentName) {
    public static ReadCategoryResponseDTO of(Category category) {
        String parentName = null; // 초기값을 null로 설정
        if (category.getParent() != null) {
            parentName = category.getParent().getName(); // 부모가 존재하면 ID를 할당
        }
        return new ReadCategoryResponseDTO(
                category.getId(), category.getName(), category.getDepth(), parentName);
    }
}
