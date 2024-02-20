package com.example.testypie.domain.commentLike.service;

import static com.example.testypie.domain.comment.constant.CommentConstant.DEFAULT_COMMENT_LIKE_CNT;
import static com.example.testypie.domain.commentLike.constant.CommentLikeConstant.DEFAULT_COMMENT_LIKE;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

import com.example.testypie.domain.comment.entity.Comment;
import com.example.testypie.domain.comment.repository.CommentRepository;
import com.example.testypie.domain.comment.service.CommentService;
import com.example.testypie.domain.commentLike.dto.response.CommentLikeResponseDto;
import com.example.testypie.domain.commentLike.entity.CommentLike;
import com.example.testypie.domain.commentLike.repository.CommentLikeRepository;
import com.example.testypie.domain.user.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentLikeServiceTest {

    @Mock private CommentLikeRepository commentLikeRepository;

    @Mock private CommentRepository commentRepository;

    @Mock private CommentService commentService;

    @InjectMocks private CommentLikeService commentLikeService;

    @DisplayName("CommentLike 1번째 클릭")
    @Test
    void firstClickCommentLike() {
        // given
        User user = User.builder().id(1L).build();

        Comment comment = Comment.builder().id(1L).commentLikeCnt(DEFAULT_COMMENT_LIKE_CNT).build();

        CommentLike commentLike =
                CommentLike.builder()
                        .id(1L)
                        .isCommentLiked(DEFAULT_COMMENT_LIKE)
                        .comment(comment)
                        .user(user)
                        .build();

        Optional<CommentLike> optionalCommentLike = Optional.empty();

        given(commentService.checkComment(anyLong())).willReturn(comment);

        given(commentLikeRepository.findByCommentAndUser(any(Comment.class), any(User.class)))
                .willReturn(optionalCommentLike);

        given(commentLikeRepository.save(any(CommentLike.class))).willReturn(commentLike);
        // when
        CommentLikeResponseDto result = commentLikeService.clickCommentLike(comment.getId(), user);

        // then
        assertThat(result.isCommentLiked()).isEqualTo(true);
        verify(commentLikeRepository, times(1)).save(any(CommentLike.class));
    }

    @DisplayName("CommentLike 2번째 클릭")
    @Test
    void secondClickCommentLike() {
        // given
        User user = User.builder().id(1L).build();

        Comment comment = Comment.builder().id(1L).commentLikeCnt(DEFAULT_COMMENT_LIKE_CNT).build();

        CommentLike commentLike =
                CommentLike.builder().id(1L).isCommentLiked(true).comment(comment).user(user).build();

        given(commentService.checkComment(anyLong())).willReturn(comment);

        given(commentLikeRepository.findByCommentAndUser(any(Comment.class), any(User.class)))
                .willReturn(Optional.ofNullable(commentLike));

        // when
        CommentLikeResponseDto result = commentLikeService.clickCommentLike(comment.getId(), user);

        // then
        assertThat(result.isCommentLiked()).isEqualTo(DEFAULT_COMMENT_LIKE);
        verify(commentLikeRepository, times(0)).save(any(CommentLike.class));
    }
}
