package com.example.testypie.domain.feedback.entity;

import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.survey.entity.Survey;
import com.example.testypie.domain.user.dto.request.RatingStarRequestDTO;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.global.TimeStamp;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Feedback extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackDetails> feedbackDetailsList = new ArrayList<>();

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne @JoinColumn private Survey survey;

    @Column private Double rating;

    @Builder
    private Feedback(Long id, Double rating, User user, Product product, Survey survey) {

        this.id = id;
        this.feedbackDetailsList = new ArrayList<>();
        this.user = user;
        this.product = product;
        this.rating = rating;
        this.survey = survey;
    }

    public void assignRating(RatingStarRequestDTO req) {
        this.rating = req.rating();
    }

    public void setFeedbackDetailsList(List<FeedbackDetails> detailslist) {

        if (detailslist != null) {
            this.feedbackDetailsList = detailslist;
            detailslist.forEach(feedbackDetails -> FeedbackDetails.builder().feedback(this).build());
        } else {
            throw new IllegalArgumentException("Feedback에 Question은 반드시 들어가야 합니다.");
        }
    }
}
