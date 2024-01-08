package com.example.testypie.feedback.entity;

import com.example.testypie.product.entity.Product;
import com.example.testypie.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double grade;

    @Column
    private String title;

    @Column
    private LocalDateTime createAt;

    @Column
    private LocalDateTime modifiedAt;

    @Column
    private String content;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Builder
    private Feedback(Long id, Double grade, String title, LocalDateTime createAt, LocalDateTime modifiedAt,
                     String content, User user, Product product) {
        this.id = id;
        this.grade = grade;
        this.title = title;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
        this.content = content;
        this.user = user;
        this.product = product;
    }
}
