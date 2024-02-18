package com.example.testypie.domain.productLike.service;

import static com.example.testypie.domain.productLike.constant.ProductLikeConstant.DEFAULT_PRODUCT_LIKE;

import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.product.service.ProductService;
import com.example.testypie.domain.productLike.dto.response.ProductLikeResponseDto;
import com.example.testypie.domain.productLike.entity.ProductLike;
import com.example.testypie.domain.productLike.repository.ProductLikeRepository;
import com.example.testypie.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductLikeService {

  private final ProductLikeRepository productLikeRepository;
  private final ProductService productService;

  @Transactional
  public ProductLikeResponseDto clickProductLike(Long productId, User user) {

    ProductAndLike result = CheckProductAndLike(productId, user);

    boolean clickProductLike = result.productLike().clickProductLike();
    result.product().updateProductLikeCnt(clickProductLike);

    return ProductLikeResponseDto.of(result.productLike().getIsProductLiked());
  }

  public ProductLikeResponseDto getProductLike(Long productId, User user) {

    ProductAndLike result = CheckProductAndLike(productId, user);

    return ProductLikeResponseDto.of(result.productLike().getIsProductLiked());
  }

  private ProductLike saveProductLike(Product product, User user) {

    ProductLike productLike =
        ProductLike.builder()
            .user(user)
            .product(product)
            .isProductLiked(DEFAULT_PRODUCT_LIKE)
            .build();

    return productLikeRepository.save(productLike);
  }

  private ProductLike getProductLikeOrElseCreateProductLike(User user, Product product) {
    return productLikeRepository
        .findByProductAndUser(product, user)
        .orElseGet(() -> saveProductLike(product, user));
  }

  private ProductAndLike CheckProductAndLike(Long productId, User user) {

    Product product = productService.checkProduct(productId);
    ProductLike productLike = getProductLikeOrElseCreateProductLike(user, product);

    return ProductAndLike.of(product, productLike);
  }

  private record ProductAndLike(Product product, ProductLike productLike) {

    private static ProductAndLike of(Product product, ProductLike productLike) {
      return new ProductAndLike(product, productLike);
    }
  }
}
