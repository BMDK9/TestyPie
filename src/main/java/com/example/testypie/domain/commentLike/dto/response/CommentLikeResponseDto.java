package com.example.testypie.domain.commentLike.dto.response;

public record CommentLikeResponseDto(Boolean isCommentLiked) {

    public static CommentLikeResponseDto of(Boolean isCommentLiked) {

        return new CommentLikeResponseDto(isCommentLiked);
    }
}
