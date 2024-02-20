package com.example.testypie.domain.feedback.repository;

import com.example.testypie.domain.feedback.entity.Feedback;
import com.example.testypie.domain.survey.entity.Survey;
import com.example.testypie.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Boolean existsByUserAndSurvey(User user, Survey survey);

    Optional<Feedback> findByProductIdAndId(Long productId, Long feedbackId);

    // account ID를 기준으로 해당 Tester가 작성한 모든 Feedback의 평균 점수를 구하는 메서드
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.user.id = :userId")
    Optional<Double> findAverageScoreByUserId(@Param("userId") Long userId);

    // 프론트 미구현
    //  @Query(
    //      "SELECT f FROM Feedback f "
    //          + "JOIN f.product p "
    //          + "WHERE p.id = :productId AND f.rating = :rating")
    //  List<Feedback> findFeedbacksByProductIdAndRating(
    //      @Param("productId") Long productId, @Param("rating") Double rating);

    Optional<List<Feedback>> findAllFeedbacksByProductId(Long productId);
}
