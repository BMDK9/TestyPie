package com.example.testypie.domain.productLike.controller;

import com.example.testypie.domain.productLike.dto.response.ProductLikeResponseDto;
import com.example.testypie.domain.productLike.service.ProductLikeService;
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
@RequestMapping("/api/category/{parentCategoryName}/{childCategoryId}/products")
public class ProductLikeController {

    private final ProductLikeService productLikeService;

    @PatchMapping("/{productId}/product_like")
    public ResponseEntity<ProductLikeResponseDto> clickProductLike(
            @PathVariable String parentCategoryName,
            @PathVariable Long childCategoryId,
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ProductLikeResponseDto res =
                productLikeService.clickProductLike(productId, userDetails.getUser());
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/{productId}/product_like/status")
    public ResponseEntity<ProductLikeResponseDto> getProductLike(
            @PathVariable String parentCategoryName,
            @PathVariable Long childCategoryId,
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ProductLikeResponseDto res =
                productLikeService.getProductLike(productId, userDetails.getUser());
        return ResponseEntity.ok().body(res);
    }
}
