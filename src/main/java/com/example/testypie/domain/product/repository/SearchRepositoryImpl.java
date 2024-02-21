package com.example.testypie.domain.product.repository;

import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.product.entity.QProduct;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepository {

    private final JPQLQueryFactory queryFactory;

    @Override
    public Page<Product> searchAllByKeyword(Pageable pageable, Long childCategoryId, String keyword) {

        QProduct product = QProduct.product;

        JPQLQuery<Product> query =
                queryFactory
                        .selectFrom(product)
                        .where(
                                product
                                        .category
                                        .id
                                        .eq(childCategoryId)
                                        .and(
                                                product
                                                        .title
                                                        .containsIgnoreCase(keyword)
                                                        .or(product.user.nickname.containsIgnoreCase(keyword))))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .orderBy(product.createdAt.desc());

        QueryResults<Product> results = query.fetchResults();
        List<Product> contents = results.getResults();
        long contentCnt = results.getTotal();

        return new PageImpl<>(contents, pageable, contentCnt);
    }
}
