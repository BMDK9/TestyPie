package com.example.testypie.domain.survey.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name = "options")
@Getter
@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text; // 선택지 내용

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question; // 해당 선택지가 속한 질문

    @Builder
    private Option(Long id, String text, Question question) {
        this.id = id;
        this.text = text;
        this.question = question;
    }
}
