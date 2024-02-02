package com.example.testypie.domain.user.repository;

import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.user.dto.ParticipatedProductResponseDTO;
import com.example.testypie.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByAccount(String account);

  Optional<User> findByEmail(String email);

  Optional<User> findByNickname(String nickname);

  @Query("SELECT p FROM Product p WHERE p.user.account = :account ORDER BY p.createdAt DESC")
  List<Product> getUserProductsOrderByCreatedAtDesc(String account);

  @Query(
      "SELECT new com.example.testypie.domain.user.dto.ParticipatedProductResponseDTO(f.product.title, f.createdAt) FROM Feedback f LEFT JOIN f.product p WHERE f.user.account = :account ORDER BY f.createdAt DESC")
  List<ParticipatedProductResponseDTO> getUserFeedbacksDtoIncludingProductInfo(String account);

  boolean existsProductById(Long productId);

  @Query("SELECT DISTINCT f.user FROM Feedback f WHERE f.product.id = :productId")
  List<User> findAllFeedbackUsersByProductId(@Param("productId") Long productId);

  Optional<User> getUserById(Long id);

  Optional<User> findByKakaoId(Long kakaoId);
}
