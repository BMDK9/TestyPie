package com.example.testypie.domain.commentLike.entity;

import com.example.testypie.domain.comment.entity.Comment;
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
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isCommentLiked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    private CommentLike(Long id, Boolean isCommentLiked, User user, Comment comment) {
        this.id = id;
        this.isCommentLiked = isCommentLiked;
        this.user = user;
        this.comment = comment;
    }

    public Boolean clickCommentLike() {
        this.isCommentLiked = !isCommentLiked;
        return this.isCommentLiked;
    }
}
