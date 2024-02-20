package com.example.testypie.domain.comment.repository;

import com.example.testypie.domain.comment.entity.Comment;
import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByProduct(Product product, Pageable pageable);

    Optional<Comment> findByProduct(Product product);

    Optional<Comment> findByUser(User user);
}
