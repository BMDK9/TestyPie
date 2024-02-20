package com.example.testypie.domain.feedback.dto.response;

import com.example.testypie.domain.feedback.entity.Feedback;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ReadFeedbackResponseDTO(
        Long id,
        LocalDateTime createdAt,
        List<ReadFeedbackDetailsResponseDTO> feedbackDetailsList,
        Long userId,
        Long productId,
        Double rating) {
    public static ReadFeedbackResponseDTO of(Feedback feedback) {

        List<ReadFeedbackDetailsResponseDTO> detailsDTO =
                feedback.getFeedbackDetailsList().stream()
                        .map(ReadFeedbackDetailsResponseDTO::of)
                        .collect(Collectors.toList());

        return new ReadFeedbackResponseDTO(
                feedback.getId(),
                feedback.getCreatedAt(),
                detailsDTO,
                feedback.getUser().getId(),
                feedback.getProduct().getId(),
                feedback.getRating());
    }
}
