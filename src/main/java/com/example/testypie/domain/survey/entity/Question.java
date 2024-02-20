package com.example.testypie.domain.survey.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @JoinColumn
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> optionList = new ArrayList<>();

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Survey survey;

    @Builder
    private Question(Long id, String text, QuestionType questionType, Survey survey) {
        this.id = id;
        this.text = text;
        this.questionType = questionType;
        this.optionList = new ArrayList<>();
        this.survey = survey;
    }
}
