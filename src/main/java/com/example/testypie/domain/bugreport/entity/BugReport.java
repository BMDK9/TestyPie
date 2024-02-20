package com.example.testypie.domain.bugreport.entity;

import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.global.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BugReport extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String content;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column private String fileUrl;

    @Builder
    private BugReport(Long id, String content, Product product, User user, String fileUrl) {
        this.id = id;
        this.content = content;
        this.product = product;
        this.user = user;
        this.fileUrl = fileUrl;
    }
}
