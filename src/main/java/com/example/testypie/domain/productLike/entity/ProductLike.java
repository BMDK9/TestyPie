package com.example.testypie.domain.productLike.entity;

import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class ProductLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isProductLiked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    private ProductLike(Long id, Boolean isProductLiked, User user, Product product) {

        this.id = id;
        this.isProductLiked = isProductLiked;
        this.user = user;
        this.product = product;
    }

    public Boolean clickProductLike() {
        this.isProductLiked = !isProductLiked;
        return this.isProductLiked;
    }
}
