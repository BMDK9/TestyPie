package com.example.testypie.domain.feedback.repository;

import com.example.testypie.domain.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findByTitle(String Title);

    List<Feedback> findAllByOrderByCreatedAtDesc();

    Optional<Feedback> findByGrade(double grade);

    Optional<Feedback> findByProductIdAndId(Long productId, Long feedbackId);
}
