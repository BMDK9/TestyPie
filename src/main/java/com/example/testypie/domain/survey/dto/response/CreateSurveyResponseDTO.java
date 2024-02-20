package com.example.testypie.domain.survey.dto.response;

import com.example.testypie.domain.survey.entity.Survey;
import java.time.LocalDateTime;

public record CreateSurveyResponseDTO(
        Long id, String title, LocalDateTime createdAt, Long productId) {
    public CreateSurveyResponseDTO(Survey survey) {
        this(survey.getId(), survey.getTitle(), survey.getCreatedAt(), survey.getProduct().getId());
    }
}
