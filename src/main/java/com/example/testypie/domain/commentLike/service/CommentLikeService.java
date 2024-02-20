package com.example.testypie.domain.commentLike.service;

import static com.example.testypie.domain.commentLike.constant.CommentLikeConstant.DEFAULT_COMMENT_LIKE;

import com.example.testypie.domain.comment.entity.Comment;
import com.example.testypie.domain.comment.service.CommentService;
import com.example.testypie.domain.commentLike.dto.response.CommentLikeResponseDto;
import com.example.testypie.domain.commentLike.entity.CommentLike;
import com.example.testypie.domain.commentLike.repository.CommentLikeRepository;
import com.example.testypie.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentService commentService;

    @Transactional
    public CommentLikeResponseDto clickCommentLike(Long commentId, User user) {

        CommentAndLike result = CheckCommentAndLike(commentId, user);

        boolean clickCommentLike = result.commentLike().clickCommentLike();
        result.comment().updateCommentLikeCnt(clickCommentLike);

        return CommentLikeResponseDto.of(result.commentLike().getIsCommentLiked());
    }

    public CommentLikeResponseDto getCommentLike(Long commentId, User user) {

        CommentAndLike result = CheckCommentAndLike(commentId, user);

        return CommentLikeResponseDto.of(result.commentLike.getIsCommentLiked());
    }

    private CommentLike saveCommentLike(Comment comment, User user) {

        CommentLike commentLike =
                CommentLike.builder()
                        .user(user)
                        .comment(comment)
                        .isCommentLiked(DEFAULT_COMMENT_LIKE)
                        .build();

        return commentLikeRepository.save(commentLike);
    }

    private CommentLike getCommentLikeOrElseCreateCommentLike(User user, Comment comment) {

        return commentLikeRepository
                .findByCommentAndUser(comment, user)
                .orElseGet(() -> saveCommentLike(comment, user));
    }

    private CommentAndLike CheckCommentAndLike(Long commentId, User user) {

        Comment comment = commentService.checkComment(commentId);
        CommentLike commentLike = getCommentLikeOrElseCreateCommentLike(user, comment);

        return CommentAndLike.of(comment, commentLike);
    }

    private record CommentAndLike(Comment comment, CommentLike commentLike) {

        private static CommentAndLike of(Comment comment, CommentLike commentLike) {
            return new CommentAndLike(comment, commentLike);
        }
    }
}
