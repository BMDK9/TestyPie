package com.example.testypie.domain.survey.dto.response;

import com.example.testypie.domain.survey.entity.Option;

public record ReadOptionResponseDTO(Long id, String text, Long questionId) {

    public static ReadOptionResponseDTO of(Option option) {
        return new ReadOptionResponseDTO(
                option.getId(), option.getText(), option.getQuestion().getId());
    }
}
