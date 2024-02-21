package com.example.testypie.domain.productLike.service;

import static com.example.testypie.domain.product.constant.ProductConstant.DEFAULT_PRODUCT_LIKE_CNT;
import static com.example.testypie.domain.productLike.constant.ProductLikeConstant.DEFAULT_PRODUCT_LIKE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.product.repository.ProductRepository;
import com.example.testypie.domain.product.service.ProductService;
import com.example.testypie.domain.productLike.dto.response.ProductLikeResponseDto;
import com.example.testypie.domain.productLike.entity.ProductLike;
import com.example.testypie.domain.productLike.repository.ProductLikeRepository;
import com.example.testypie.domain.user.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductLikeServiceTest {

    @Mock private ProductLikeRepository productLikeRepository;

    @Mock private ProductRepository productRepository;

    @Mock private ProductService productService;

    @InjectMocks private ProductLikeService productLikeService;

    @DisplayName("ProductLike 1번째 클릭")
    @Test
    void firstClickProductLike() {
        // given
        User user = User.builder().id(1L).build();

        Product product = Product.builder().id(1L).productLikeCnt(DEFAULT_PRODUCT_LIKE_CNT).build();

        ProductLike productLike =
                ProductLike.builder()
                        .id(1L)
                        .isProductLiked(DEFAULT_PRODUCT_LIKE)
                        .product(product)
                        .user(user)
                        .build();

        Optional<ProductLike> optionalProductLike = Optional.empty();

        given(productService.checkProduct(anyLong())).willReturn(product);

        given(productLikeRepository.findByProductAndUser(any(Product.class), any(User.class)))
                .willReturn(optionalProductLike);

        given(productLikeRepository.save(any(ProductLike.class))).willReturn(productLike);

        // when
        ProductLikeResponseDto result = productLikeService.clickProductLike(product.getId(), user);

        // then
        assertThat(result.isProductLiked()).isEqualTo(true);
        verify(productLikeRepository, times(1)).save(any(ProductLike.class));
    }

    @DisplayName("ProductLike 2번째 클릭")
    @Test
    void secondClickProductLike() {
        // given
        User user = User.builder().id(1L).build();

        Product product = Product.builder().id(1L).productLikeCnt(DEFAULT_PRODUCT_LIKE_CNT).build();

        ProductLike productLike =
                ProductLike.builder().id(1L).isProductLiked(true).product(product).user(user).build();

        given(productService.checkProduct(anyLong())).willReturn(product);

        given(productLikeRepository.findByProductAndUser(any(Product.class), any(User.class)))
                .willReturn(Optional.ofNullable(productLike));

        // when
        ProductLikeResponseDto result = productLikeService.clickProductLike(product.getId(), user);

        // then
        assertThat(result.isProductLiked()).isEqualTo(DEFAULT_PRODUCT_LIKE);
        verify(productLikeRepository, times(0)).save(any(ProductLike.class));
    }
}
