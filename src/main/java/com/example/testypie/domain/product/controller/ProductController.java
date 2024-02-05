package com.example.testypie.domain.product.controller;

import com.example.testypie.domain.product.dto.request.CreateProductRequestDTO;
import com.example.testypie.domain.product.dto.request.UpdateProductRequestDTO;
import com.example.testypie.domain.product.dto.response.CreateProductResponseDTO;
import com.example.testypie.domain.product.dto.response.DeleteProductResponseDTO;
import com.example.testypie.domain.product.dto.response.ProductPageResponseDTO;
import com.example.testypie.domain.product.dto.response.ReadProductResponseDTO;
import com.example.testypie.domain.product.dto.response.SearchProductResponseDTO;
import com.example.testypie.domain.product.dto.response.UpdateProductResponseDTO;
import com.example.testypie.domain.product.service.ProductService;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  // Product 생성
  @PostMapping("/category/{parentCategory_name}/{childCategory_id}/products")
  public ResponseEntity<CreateProductResponseDTO> createPost(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @Valid @RequestBody CreateProductRequestDTO req,
      @PathVariable Long childCategory_id,
      @PathVariable String parentCategory_name) {

    User user = userDetails.getUser();
    CreateProductResponseDTO res =
        productService.createProduct(user, req, parentCategory_name, childCategory_id);
    return ResponseEntity.status(HttpStatus.CREATED).body(res);
  }

  // Product 단일 조회
  @GetMapping("/category/{parentCategory_name}/{childCategory_id}/products/{productId}")
  public ModelAndView getProduct(
      @PathVariable Long productId,
      @PathVariable Long childCategory_id,
      @PathVariable String parentCategory_name,
      Model model)
      throws ParseException {

    ReadProductResponseDTO res =
        productService.getProduct(productId, childCategory_id, parentCategory_name);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("product");
    model.addAttribute("parentCategory_name", parentCategory_name);
    model.addAttribute("childCategory_id", childCategory_id);
    model.addAttribute("product", res);
    return modelAndView;
  }

  // Product 전체 조회 및 카테고리 조회(페이징)
  @GetMapping("/category/{parentCategory_name}")
  public ResponseEntity<Page<ProductPageResponseDTO>> getProductPage(
      // (page = 1) => 1페이지부터 시작
      @PageableDefault(page = 1) Pageable pageable, @PathVariable String parentCategory_name)
      throws ParseException {

    Page<ProductPageResponseDTO> res = productService.getProductPage(pageable, parentCategory_name);

    return ResponseEntity.ok().body(res);
  }

  @GetMapping("/category/{parentCategory_name}/{childCategory_id}")
  public ModelAndView getProductCategoryPage(
      // (page = 1) => 1페이지부터 시작
      @PageableDefault(page = 1) Pageable pageable,
      @PathVariable(required = false) Long childCategory_id,
      @PathVariable String parentCategory_name,
      Model model)
      throws ParseException {

    Page<ProductPageResponseDTO> res =
        productService.getProductCategoryPage(pageable, childCategory_id, parentCategory_name);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("productList");
    model.addAttribute("productList", res);
    return modelAndView;
  }

  @GetMapping("/category/{parentCategory_name}/{childCategory_id}/search")
  public ResponseEntity<Page<SearchProductResponseDTO>> searchProduct(
      @PageableDefault(page = 1, sort = "productId", direction = Direction.DESC) Pageable pageable,
      @PathVariable String parentCategory_name,
      @PathVariable Long childCategory_id,
      @RequestParam String keyword)
      throws ParseException {

    Page<SearchProductResponseDTO> resList =
        productService.searchProductList(pageable, childCategory_id, keyword);

    return ResponseEntity.ok().body(resList);
  }

  // Product 좋아요 순 조회
  @GetMapping("/products/like")
  public ResponseEntity<Page<ProductPageResponseDTO>> getProductPageOrderByLikeDesc(
      // (page = 1) => 1페이지부터 시작
      @PageableDefault(page = 1) Pageable pageable) throws ParseException {

    Page<ProductPageResponseDTO> res = productService.getProductPageOrderByLikeDesc(pageable);

    return ResponseEntity.ok().body(res);
  }

  // Product 수정
  @PatchMapping("/category/{parentCategory_name}/{childCategory_id}/products/{productId}/update")
  public ResponseEntity<UpdateProductResponseDTO> updateProduct(
      @PathVariable Long productId,
      @RequestBody UpdateProductRequestDTO req,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long childCategory_id,
      @PathVariable String parentCategory_name) {

    UpdateProductResponseDTO res =
        productService.updateProduct(
            productId, req, userDetails.getUser(), childCategory_id, parentCategory_name);
    return ResponseEntity.ok().body(res);
  }

  // Product 삭제
  @DeleteMapping("/category/{parentCategory_name}/{childCategory_id}/products/{productId}/delete")
  public ResponseEntity<DeleteProductResponseDTO> deleteProduct(
      @PathVariable Long productId,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long childCategory_id,
      @PathVariable String parentCategory_name) {

    DeleteProductResponseDTO res =
        productService.deleteProduct(
            productId, userDetails.getUser(), childCategory_id, parentCategory_name);
    return ResponseEntity.ok().body(res);
  }
}
