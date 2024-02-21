package com.example.testypie.domain.comment.controller;

import com.example.testypie.domain.comment.dto.request.CreateCommentRequestDTO;
import com.example.testypie.domain.comment.dto.request.UpdateCommentRequestDTO;
import com.example.testypie.domain.comment.dto.response.CreateCommentResponseDTO;
import com.example.testypie.domain.comment.dto.response.ReadPageCommentResponseDTO;
import com.example.testypie.domain.comment.dto.response.UpdateCommentResponseDTO;
import com.example.testypie.domain.core.service.CommentMatcherService;
import com.example.testypie.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentMatcherService commentMatcherService;

    @PostMapping("/category/{parentCategoryName}/{childCategoryId}/products/{productId}/comments")
    public ResponseEntity<CreateCommentResponseDTO> createComment(
            @PathVariable Long productId,
            @Valid @RequestBody CreateCommentRequestDTO req,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long childCategoryId,
            @PathVariable String parentCategoryName) {

        CreateCommentResponseDTO res =
                commentMatcherService.productComment(
                        productId, req, userDetails.getUser(), childCategoryId, parentCategoryName);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/category/{parentCategoryName}/{childCategoryId}/products/{productId}/comments")
    public ResponseEntity<Page<ReadPageCommentResponseDTO>> getCommentPage(
            @PageableDefault(page = 1, sort = "comment_id", direction = Direction.DESC) Pageable pageable,
            @PathVariable Long productId,
            @PathVariable Long childCategoryId,
            @PathVariable String parentCategoryName) {

        Page<ReadPageCommentResponseDTO> resList =
                commentMatcherService.getCommentPage(
                        pageable, productId, childCategoryId, parentCategoryName);

        return ResponseEntity.ok().body(resList);
    }

    @PatchMapping(
            "/category/{parentCategoryName}/{childCategoryId}/products/{productId}/comments/{comment_id}")
    public ResponseEntity<UpdateCommentResponseDTO> updateComment(
            @PathVariable Long productId,
            @PathVariable Long comment_id,
            @Valid @RequestBody UpdateCommentRequestDTO req,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long childCategoryId,
            @PathVariable String parentCategoryName) {

        UpdateCommentResponseDTO res =
                commentMatcherService.updateComment(
                        productId, comment_id, req, userDetails.getUser(), childCategoryId, parentCategoryName);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping(
            "/category/{parentCategoryName}/{childCategoryId}/products/{productId}/comments/{comment_id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long productId,
            @PathVariable Long comment_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long childCategoryId,
            @PathVariable String parentCategoryName) {

        commentMatcherService.deleteComment(
                productId, comment_id, userDetails.getUser(), childCategoryId, parentCategoryName);

        return ResponseEntity.noContent().build();
    }
}
