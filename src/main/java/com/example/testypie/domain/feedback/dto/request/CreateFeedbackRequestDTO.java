package com.example.testypie.domain.feedback.dto.request;

import java.util.List;
import lombok.NonNull;

public record CreateFeedbackRequestDTO(
        @NonNull List<CreateFeedbackDetailsRequestDTO> feedbackDetailsList) {}
