package com.example.testypie.domain.commentLike.repository;

import com.example.testypie.domain.comment.entity.Comment;
import com.example.testypie.domain.commentLike.entity.CommentLike;
import com.example.testypie.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);
}
