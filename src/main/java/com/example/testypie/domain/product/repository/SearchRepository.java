package com.example.testypie.domain.product.repository;

import com.example.testypie.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository {

    Page<Product> searchAllByKeyword(Pageable pageable, Long childCategoryId, String keyWord);
}
