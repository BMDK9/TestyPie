package com.example.testypie.feedback.controller;

import com.example.testypie.feedback.dto.FeedbackRequestDTO;
import com.example.testypie.feedback.dto.FeedbackResponseDTO;
import com.example.testypie.feedback.service.FeedbackService;
import com.example.testypie.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/{product_id}/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping("/{feedback_id}")
    public ResponseEntity<FeedbackResponseDTO> getFeedback(
        @PathVariable Long feedback_id,
        @PathVariable Long product_id) {
        FeedbackResponseDTO res = feedbackService.getFeedback(feedback_id, product_id);
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbacks(@PathVariable Long product_id) {
        List<FeedbackResponseDTO> resList = feedbackService.getFeedbacks(product_id);
        return ResponseEntity.ok(resList);
    }

    @PostMapping
    public ResponseEntity<FeedbackResponseDTO> addFeedback(@RequestBody FeedbackRequestDTO req, @AuthenticationPrincipal
        UserDetailsImpl userDetails, @PathVariable Long product_id) {
        FeedbackResponseDTO res = feedbackService.addFeedback(req, product_id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PatchMapping("/{feedback_id}")
    public ResponseEntity<FeedbackResponseDTO> updateFeedback(
        @PathVariable Long product_id,
        @Valid @RequestBody FeedbackRequestDTO req,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long feedback_id) {
        FeedbackResponseDTO res = feedbackService.updateFeedback(product_id, req, userDetails.getUser(), feedback_id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{feedback_id}")
    public ResponseEntity<Void> deleteFeedback(
            @PathVariable Long product_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long feedback_id) {
        feedbackService.deleteFeedback(product_id, userDetails.getUser(), feedback_id);
        return ResponseEntity.noContent().build();
    }
}
