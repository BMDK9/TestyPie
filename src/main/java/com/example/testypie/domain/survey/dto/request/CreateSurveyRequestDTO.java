package com.example.testypie.domain.survey.dto.request;

import java.util.List;
import lombok.NonNull;

public record CreateSurveyRequestDTO(
        @NonNull String title, @NonNull List<CreateQuestionRequestDTO> questionList) {}
