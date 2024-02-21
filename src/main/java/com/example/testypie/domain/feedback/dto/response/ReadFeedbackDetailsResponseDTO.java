package com.example.testypie.domain.feedback.dto.response;

import com.example.testypie.domain.feedback.entity.FeedbackDetails;

public record ReadFeedbackDetailsResponseDTO(Long id, String response, Long feedbackId) {
    public static ReadFeedbackDetailsResponseDTO of(FeedbackDetails feedbackDetails) {

        return new ReadFeedbackDetailsResponseDTO(
                feedbackDetails.getId(),
                feedbackDetails.getResponse(),
                feedbackDetails.getFeedback().getId());
    }
}
