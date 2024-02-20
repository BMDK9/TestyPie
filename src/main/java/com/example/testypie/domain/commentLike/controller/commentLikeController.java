package com.example.testypie.domain.commentLike.controller;

import com.example.testypie.domain.commentLike.dto.response.CommentLikeResponseDto;
import com.example.testypie.domain.commentLike.service.CommentLikeService;
import com.example.testypie.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        "/api/category/{parentCategoryName}/{childCategoryId}/products/{productId}/comments")
public class commentLikeController {

    private final CommentLikeService commentLikeService;

    @PatchMapping("/{comment_id}/comment_like")
    public ResponseEntity<CommentLikeResponseDto> clickCommentLike(
            @PathVariable String parentCategoryName,
            @PathVariable Long childCategoryId,
            @PathVariable Long productId,
            @PathVariable Long comment_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommentLikeResponseDto res =
                commentLikeService.clickCommentLike(comment_id, userDetails.getUser());

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/{comment_id}/comment_like/status")
    public ResponseEntity<CommentLikeResponseDto> getCommentLike(
            @PathVariable String parentCategoryName,
            @PathVariable Long childCategoryId,
            @PathVariable Long productId,
            @PathVariable Long comment_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommentLikeResponseDto res =
                commentLikeService.getCommentLike(comment_id, userDetails.getUser());

        return ResponseEntity.ok().body(res);
    }
}
