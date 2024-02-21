package com.example.testypie.domain.survey.dto.response;

import com.example.testypie.domain.survey.entity.Survey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ReadSurveyResponseDTO(
        Long id, String title, LocalDateTime createAt, List<ReadQuestionResponseDTO> questionList) {
    public static ReadSurveyResponseDTO of(Survey survey) {

        List<ReadQuestionResponseDTO> questions =
                survey.getQuestionList().stream()
                        .map(ReadQuestionResponseDTO::of)
                        .collect(Collectors.toList());

        return new ReadSurveyResponseDTO(
                survey.getId(), survey.getTitle(), survey.getCreatedAt(), questions);
    }
}
