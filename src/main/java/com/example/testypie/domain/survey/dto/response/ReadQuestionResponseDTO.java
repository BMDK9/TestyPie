package com.example.testypie.domain.survey.dto.response;

import com.example.testypie.domain.survey.entity.Question;
import com.example.testypie.domain.survey.entity.QuestionType;
import java.util.List;
import java.util.stream.Collectors;

public record ReadQuestionResponseDTO(
        Long id,
        String text,
        QuestionType questionType,
        List<ReadOptionResponseDTO> optionList,
        Long surveyId) {

    public static ReadQuestionResponseDTO of(Question question) {
        List<ReadOptionResponseDTO> optionList =
                question.getOptionList().stream()
                        .map(ReadOptionResponseDTO::of)
                        .collect(Collectors.toList());
        return new ReadQuestionResponseDTO(
                question.getId(),
                question.getText(),
                question.getQuestionType(),
                optionList,
                question.getSurvey().getId());
    }
}
