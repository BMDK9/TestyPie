package com.example.testypie.domain.feedback.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class FeedbackDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Feedback feedback;

    @Column
    private String response;

    @Builder
    public FeedbackDetails(Long id, Feedback feedback, String response){
        this.id = id;
        this.feedback = feedback;
        this.response = response;
    }
}
