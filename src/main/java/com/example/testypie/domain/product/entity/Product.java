package com.example.testypie.domain.product.entity;

import com.example.testypie.domain.category.entity.Category;
import com.example.testypie.domain.comment.entity.Comment;
import com.example.testypie.domain.feedback.entity.Feedback;
import com.example.testypie.domain.productLike.entity.ProductLike;
import com.example.testypie.domain.reward.entity.Reward;
import com.example.testypie.domain.survey.entity.Survey;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.global.TimeStamp;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Product extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column private String title;

    @Column private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Category category;

    @Column(nullable = false)
    private Long productLikeCnt;

    @Column private LocalDateTime startedAt;

    @Column private LocalDateTime closedAt;

    @OneToMany(
            mappedBy = "product",
            targetEntity = Comment.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<Comment> commentList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "product",
            targetEntity = Reward.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Reward> rewardList = new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Feedback> feedback = new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            targetEntity = ProductLike.class,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<ProductLike> productLikeList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Builder
    private Product(
            Long id,
            User user,
            String title,
            String content,
            Category category,
            List<Reward> rewardList,
            LocalDateTime startedAt,
            LocalDateTime closedAt,
            List<Comment> commentList,
            Long productLikeCnt,
            Survey survey) {

        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category;
        this.rewardList = rewardList;
        this.startedAt = startedAt;
        this.closedAt = closedAt;
        this.commentList = commentList;
        this.productLikeCnt = productLikeCnt;
        this.survey = survey;
    }

    public void updateTitle(String title) {
        if (!title.isEmpty()) {
            this.title = title;
        }
    }

    public void updateContent(String content) {
        if (!content.isEmpty()) {
            this.content = content;
        }
    }

    public void updateStartAt(LocalDateTime startedAt) {
        if (startedAt != null) {
            this.startedAt = startedAt;
        }
    }

    public void updateClosedAt(LocalDateTime closedAt) {
        if (closedAt != null) {
            this.closedAt = closedAt;
        }
    }

    public void updateCategory(Category category) {
        if (category != null) {
            this.category = category;
        }
    }

    public void setRewardList(List<Reward> rewardList) {
        if (rewardList != null) {
            this.rewardList = rewardList;
            rewardList.forEach(reward -> Reward.builder().product(this).build());
        } else {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.PRODUCT_REWARD_IS_NOT_NULL);
        }
    }

    public void updateProductLikeCnt(boolean clickProductLike) {
        if (clickProductLike) {
            this.productLikeCnt++;
            return;
        }
        this.productLikeCnt--;
    }

    public void setSurvey(Survey survey) {
        if (survey != null) {
            this.survey = survey;
        } else {
            throw new IllegalArgumentException("Product에 설문지는 반드시 들어가야 합니다.");
        }
    }
}
