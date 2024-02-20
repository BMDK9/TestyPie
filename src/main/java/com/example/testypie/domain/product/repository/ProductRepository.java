package com.example.testypie.domain.product.repository;

import com.example.testypie.domain.product.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, SearchRepository {

    Page<Product> findAllByCategory_id(Long childCategory_id, PageRequest id);

    List<Product> findBySurveyIdIsNull();

    @Query("SELECT p FROM Product p ORDER BY p.productLikeCnt DESC")
    Page<Product> findAllSortedByProductLikeCnt(Pageable pageable);

    @Query(
            "SELECT p FROM Product p WHERE p.category IN (SELECT c FROM Category c WHERE c.parent.id = :parentId)")
    Page<Product> findByParentCategoryId(@Param("parentId") Long parentId, Pageable pageable);
}
