package com.example.testypie.domain.comment.dto.response;

import com.example.testypie.domain.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

public record UpdateCommentResponseDTO(
        Long id,
        String content,
        Long commentLikeCnt,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        String nickname,
        @JsonIgnore Long productId) {

    public static UpdateCommentResponseDTO of(Comment saveComment) {
        return new UpdateCommentResponseDTO(
                saveComment.getId(),
                saveComment.getContent(),
                saveComment.getCommentLikeCnt(),
                saveComment.getCreatedAt(),
                saveComment.getModifiedAt(),
                saveComment.getUser().getNickname(),
                saveComment.getProduct().getId());
    }
}
