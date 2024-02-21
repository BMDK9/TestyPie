package com.example.testypie.domain.comment.service;

import static com.example.testypie.domain.comment.constant.CommentConstant.DEFAULT_COMMENT_LIKE_CNT;

import com.example.testypie.domain.category.entity.Category;
import com.example.testypie.domain.comment.dto.request.CreateCommentRequestDTO;
import com.example.testypie.domain.comment.dto.request.UpdateCommentRequestDTO;
import com.example.testypie.domain.comment.dto.response.CreateCommentResponseDTO;
import com.example.testypie.domain.comment.dto.response.ReadPageCommentResponseDTO;
import com.example.testypie.domain.comment.dto.response.UpdateCommentResponseDTO;
import com.example.testypie.domain.comment.entity.Comment;
import com.example.testypie.domain.comment.repository.CommentRepository;
import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CreateCommentResponseDTO createComment(
            Category category, Product product, User user, CreateCommentRequestDTO req) {

        if (category.getId().equals(product.getCategory().getId())) {
            Comment comment =
                    Comment.builder()
                            .user(user)
                            .content(req.content())
                            .commentLikeCnt(DEFAULT_COMMENT_LIKE_CNT)
                            .product(product)
                            .build();

            Comment saveComment = commentRepository.save(comment);

            return CreateCommentResponseDTO.of(saveComment);
        } else {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_COMMENT_NOT_MATCH_ORIGIN);
        }
    }

    public Page<ReadPageCommentResponseDTO> getCommentPage(
            Pageable pageable, Category category, Product product) {

        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        if (category.getId().equals(product.getCategory().getId())) {
            Page<Comment> commentPage =
                    commentRepository.findAllByProduct(
                            product, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

            List<ReadPageCommentResponseDTO> resList = new ArrayList<>();

            for (Comment comment : commentPage) {
                ReadPageCommentResponseDTO res = ReadPageCommentResponseDTO.of(comment);
                resList.add(res);
            }
            return new PageImpl<>(resList, pageable, commentPage.getTotalElements());
        }

        throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_COMMENT_NOT_MATCH_ORIGIN);
    }

    @Transactional
    public UpdateCommentResponseDTO updateComment(
            Category category, Product product, User user, Long comment_id, UpdateCommentRequestDTO req) {

        if (category.getId().equals(product.getCategory().getId())) {
            Comment comment = checkComment(comment_id);
            checkCommentLocation(comment, product.getId());
            checkCommentUser(comment, user.getId());
            comment.update(req, product);

            return UpdateCommentResponseDTO.of(comment);
        } else {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_COMMENT_NOT_MATCH_ORIGIN);
        }
    }

    public void deleteComment(Category category, Product product, User user, Long comment_id) {

        if (category.getId().equals(product.getCategory().getId())) {
            Comment comment = checkComment(comment_id);
            checkCommentLocation(comment, product.getId());
            checkCommentUser(comment, user.getId());
            commentRepository.delete(comment);
        } else {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_COMMENT_NOT_MATCH_ORIGIN);
        }
    }

    @Transactional(readOnly = true)
    public Comment checkComment(Long comment_id) {

        return commentRepository
                .findById(comment_id)
                .orElseThrow(
                        () -> new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_COMMENT_NOT_EXIST));
    }

    public void checkCommentLocation(Comment comment, Long productId) {

        if (!comment.getProduct().getId().equals(productId)) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_COMMENT_NOT_MATCH_ORIGIN);
        }
    }

    public void checkCommentUser(Comment comment, Long userId) {

        if (!comment.getUser().getId().equals(userId)) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_COMMENT_INVALID_USER);
        }
    }
}
