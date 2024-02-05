package com.example.testypie.domain.core.service;

import com.example.testypie.domain.category.entity.Category;
import com.example.testypie.domain.category.service.CategoryService;
import com.example.testypie.domain.comment.dto.request.CreateCommentRequestDTO;
import com.example.testypie.domain.comment.dto.request.UpdateCommentRequestDTO;
import com.example.testypie.domain.comment.dto.response.CreateCommentResponseDTO;
import com.example.testypie.domain.comment.dto.response.ReadPageCommentResponseDTO;
import com.example.testypie.domain.comment.dto.response.UpdateCommentResponseDTO;
import com.example.testypie.domain.comment.service.CommentService;
import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.product.service.ProductService;
import com.example.testypie.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentMatcherService {

  private final CommentService commentService;
  private final ProductService productService;
  private final CategoryService categoryService;

  public CreateCommentResponseDTO productComment(
      Long product_id,
      CreateCommentRequestDTO req,
      User user,
      Long childCategory_id,
      String parentCategory_name) {

    CategoryAndProduct result =
        CheckCategoryAndProduct(product_id, childCategory_id, parentCategory_name);

    return commentService.createComment(result.category(), result.product(), user, req);
  }

  public Page<ReadPageCommentResponseDTO> getComments(
      Pageable pageable, Long product_id, Long childCategory_id, String parentCategory_name) {

    CategoryAndProduct result =
        CheckCategoryAndProduct(product_id, childCategory_id, parentCategory_name);

    return commentService.getComments(pageable, result.category, result.product);
  }

  @Transactional
  public UpdateCommentResponseDTO updateComment(
      Long product_id,
      Long comment_id,
      UpdateCommentRequestDTO req,
      User user,
      Long childCategory_id,
      String parentCategory_name) {

    CategoryAndProduct result =
        CheckCategoryAndProduct(product_id, childCategory_id, parentCategory_name);

    return commentService.updateComment(result.category, result.product, user, comment_id, req);
  }

  public void deleteComment(
      Long product_id,
      Long comment_id,
      User user,
      Long childCategory_id,
      String parentCategory_name) {

    CategoryAndProduct result =
        CheckCategoryAndProduct(product_id, childCategory_id, parentCategory_name);

    commentService.deleteComment(result.category, result.product, user, comment_id);
  }

  private CategoryAndProduct CheckCategoryAndProduct(
      Long product_id, Long childCategory_id, String parentCategory_name) {

    Category category = categoryService.getCategory(childCategory_id, parentCategory_name);
    Product product = productService.findProduct(product_id);
    CategoryAndProduct result = new CategoryAndProduct(category, product);

    return result;
  }

  private record CategoryAndProduct(Category category, Product product) {}
}
