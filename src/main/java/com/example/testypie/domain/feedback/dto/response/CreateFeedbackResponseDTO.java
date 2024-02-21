package com.example.testypie.domain.feedback.dto.response;

import com.example.testypie.domain.feedback.entity.Feedback;
import java.time.LocalDateTime;

public record CreateFeedbackResponseDTO(
        Long id, LocalDateTime createdAt, Long productId, Long userId) {
    public CreateFeedbackResponseDTO(Feedback feedback) {

        this(
                feedback.getId(),
                feedback.getCreatedAt(),
                feedback.getProduct().getId(),
                feedback.getUser().getId());
    }
}
