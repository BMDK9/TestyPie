package com.example.testypie.domain.feedback.repository;

import com.example.testypie.domain.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findAllByOrderByCreatedAtDesc();

    Optional<Feedback> findByProductIdAndId(Long productId, Long feedbackId);

    // account ID를 기준으로 해당 Tester가 작성한 모든 Feedback의 평균 점수를 구하는 메서드
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.user.id = :userId")
    Double findAverageScoreByUserId(@Param("userId")Long userId);
}
