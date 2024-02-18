package com.example.testypie.domain.scheduler;

import com.example.testypie.domain.commentLike.entity.CommentLike;
import com.example.testypie.domain.commentLike.repository.CommentLikeRepository;
import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.product.repository.ProductRepository;
import com.example.testypie.domain.productLike.entity.ProductLike;
import com.example.testypie.domain.productLike.repository.ProductLikeRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "스케줄러")
@RequiredArgsConstructor
@Transactional
public class Scheduler {

  private final ProductRepository productRepository;
  private final ProductLikeRepository productLikeRepository;
  private final CommentLikeRepository commentLikeRepository;

  @Transactional
  @Scheduled(cron = "0 0 0 * * *")
  public void autoDelete() {

    LocalDateTime now = LocalDateTime.now();

    List<Product> productList = productRepository.findAll();

    for (Product p : productList) {
      if (now.isAfter(p.getClosedAt()) || now.isEqual(p.getClosedAt())) {
        productRepository.delete(p);
      }
    }
  }

  @Transactional
  @Scheduled(cron = "0 0 3 * * *")
  public void productLikeAutoDelete() {

    List<ProductLike> productLikes = productLikeRepository.findAll();

    for (ProductLike isLiked : productLikes) {
      if (!isLiked.getIsProductLiked()) {
        productLikeRepository.delete(isLiked);
      }
    }
  }

  @Transactional
  @Scheduled(cron = "0 0 3 * * *")
  public void commentLikeAutoDelete() {

    List<CommentLike> commentLikes = commentLikeRepository.findAll();

    for (CommentLike isLiked : commentLikes) {
      if (!isLiked.getIsCommentLiked()) {
        commentLikeRepository.delete(isLiked);
      }
    }
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void deleteProductsWithoutSurveyId() {

    List<Product> products = productRepository.findBySurveyIdIsNull();
    productRepository.deleteAll(products);
  }
}
