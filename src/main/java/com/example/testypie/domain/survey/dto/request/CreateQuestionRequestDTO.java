package com.example.testypie.domain.survey.dto.request;

import com.example.testypie.domain.survey.entity.QuestionType;
import java.util.List;
import lombok.NonNull;

public record CreateQuestionRequestDTO(
        @NonNull String text,
        @NonNull QuestionType questionType,
        List<CreateOptionRequestDTO> optionList) {}
