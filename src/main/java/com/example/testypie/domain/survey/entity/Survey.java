package com.example.testypie.domain.survey.entity;

import com.example.testypie.domain.product.entity.Product;
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
public class Survey extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String title;

    @JoinColumn(name = "survey_id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Question> questionList = new ArrayList<>();

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    private Survey(Long id, String title, List<Question> questionList, User user, Product product) {
        this.id = id;
        this.title = title;
        this.questionList = questionList;
        this.user = user;
        this.product = product;
    }

    public void setQuestionList(List<Question> questionList) {
        if (questionList != null) {
            this.questionList = questionList;
            questionList.forEach(question -> Question.builder().survey(this).build());
        } else {
            throw new IllegalArgumentException("Feedback에 Question은 반드시 들어가야 합니다.");
        }
    }
}
