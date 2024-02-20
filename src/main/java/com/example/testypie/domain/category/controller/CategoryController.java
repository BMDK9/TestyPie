package com.example.testypie.domain.category.controller;

import com.example.testypie.domain.category.dto.request.CreateCategoryRequestDTO;
import com.example.testypie.domain.category.dto.response.CreateCategoryResponseDTO;
import com.example.testypie.domain.category.dto.response.ReadCategoryResponseDTO;
import com.example.testypie.domain.category.repository.CategoryRepository;
import com.example.testypie.domain.category.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    // Category 생성(관리자 권한 추가 예정 / UserRole == Admin)
    @PostMapping
    public ResponseEntity<CreateCategoryResponseDTO> createCategory(
            @Valid @RequestBody CreateCategoryRequestDTO req) {

        CreateCategoryResponseDTO res = categoryService.createCategory(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping
    public List<ReadCategoryResponseDTO> getCategoryList() {

        return categoryRepository.findAll().stream()
                .map(ReadCategoryResponseDTO::of)
                .collect(Collectors.toList());
    }
}
