package com.example.testypie.domain.product.dto.request;

import java.util.Optional;

public record CreateProductRequestDTO(
        CreateProductCommonRequestDTO commonCreateRequestDTO,
        Optional<CreateProductTestRequestDTO> testCreateRequestDTO) {}
