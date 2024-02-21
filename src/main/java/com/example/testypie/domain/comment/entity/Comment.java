package com.example.testypie.domain.comment.entity;

import com.example.testypie.domain.comment.dto.request.UpdateCommentRequestDTO;
import com.example.testypie.domain.commentLike.entity.CommentLike;
import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.global.TimeStamp;
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
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private Long commentLikeCnt;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @OneToMany(
            mappedBy = "comment",
            targetEntity = CommentLike.class,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    List<CommentLike> commentLikeList = new ArrayList<>();

    @Builder
    private Comment(Long id, String content, Long commentLikeCnt, User user, Product product) {
        this.id = id;
        this.content = content;
        this.commentLikeCnt = commentLikeCnt;
        this.user = user;
        this.product = product;
    }

    public void update(UpdateCommentRequestDTO req, Product product) {
        if (!req.content().isEmpty()) {
            this.content = req.content();
            this.product = product;
        }
    }

    public void updateCommentLikeCnt(boolean clickCommentLike) {
        if (clickCommentLike) {
            this.commentLikeCnt++;
            return;
        }
        this.commentLikeCnt--;
    }
}
