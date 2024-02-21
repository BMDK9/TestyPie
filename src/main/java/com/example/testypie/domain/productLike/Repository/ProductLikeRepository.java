package com.example.testypie.domain.productLike.repository;

import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.productLike.entity.ProductLike;
import com.example.testypie.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    Optional<ProductLike> findByProductAndUser(Product product, User user);
}
