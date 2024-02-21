package com.example.testypie.domain.category.service;

import com.example.testypie.domain.category.dto.request.CreateCategoryRequestDTO;
import com.example.testypie.domain.category.dto.response.CreateCategoryResponseDTO;
import com.example.testypie.domain.category.entity.Category;
import com.example.testypie.domain.category.repository.CategoryRepository;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CreateCategoryResponseDTO createCategory(CreateCategoryRequestDTO req) {

        Category parentName = categoryRepository.findByName(req.parent());
        Category category =
                Category.builder().name(req.name()).depth((long) 1).parent(parentName).build();
        Category saveCategory = categoryRepository.save(category);
        return CreateCategoryResponseDTO.of(saveCategory);
    }

    public Category getParentCategory(String parentCategoryName) {

        return categoryRepository.findByName(parentCategoryName);
    }

    public Category checkCategory(Long categoryId, String parentCategoryName) {

        Category childCategory =
                categoryRepository
                        .findById(categoryId)
                        .orElseThrow(
                                () ->
                                        new GlobalExceptionHandler.CustomException(
                                                ErrorCode.SELECT_CATEGORY_NOT_FOUND));

        Category parentCategory = categoryRepository.findByName(parentCategoryName);

        if (childCategory.getParent() == parentCategory) {
            return childCategory;
        } else {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_CATEGORY_NOT_MATCH);
        }
    }
}
