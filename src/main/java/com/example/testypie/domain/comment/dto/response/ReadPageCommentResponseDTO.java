package com.example.testypie.domain.comment.dto.response;

import com.example.testypie.domain.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

public record ReadPageCommentResponseDTO(
        Long id,
        String content,
        Long commentLikeCnt,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        String nickname,
        @JsonIgnore Long productId) {

    public static ReadPageCommentResponseDTO of(Comment saveComment) {
        return new ReadPageCommentResponseDTO(
                saveComment.getId(),
                saveComment.getContent(),
                saveComment.getCommentLikeCnt(),
                saveComment.getCreatedAt(),
                saveComment.getModifiedAt(),
                saveComment.getUser().getNickname(),
                saveComment.getProduct().getId());
    }
}
