package com.example.testypie.domain.product.dto.response;

import com.example.testypie.domain.product.entity.Product;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public record ProductPageResponseDTO(
        Long id,
        String account,
        String nickname,
        String title,
        String content,
        Long childCategoryId,
        String parentCategoryName,
        Long productLikeCnt,
        LocalDateTime createAt,
        LocalDateTime startAt,
        LocalDateTime closedAt,
        String message) {

    public static ProductPageResponseDTO of(Product product) throws ParseException {

        long diffDays;
        String message = null;

        if (product.getClosedAt() != null && product.getStartedAt() != null) {
            String end = product.getClosedAt().toString();
            String now = LocalDateTime.now().toString();

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Date nowFormat = format.parse(now);
            Date endFormat = format.parse(end);

            if (LocalDateTime.now().isBefore(product.getStartedAt())) {
                message = "테스트 시작 전입니다.";
            } else if (LocalDateTime.now().isAfter(product.getClosedAt())) {
                message = "마감된 테스트 입니다.";
            } else {
                long diffSec = (endFormat.getTime() - nowFormat.getTime()) / 1000;
                diffDays = (diffSec / (24 * 60 * 60));
                message = "마감 " + diffDays + "일 전";
            }
        }

        return new ProductPageResponseDTO(
                product.getId(),
                product.getUser().getAccount(),
                product.getUser().getNickname(),
                product.getTitle(),
                product.getContent(),
                product.getCategory().getId(),
                product.getCategory().getParent().getName(),
                product.getProductLikeCnt(),
                product.getCreatedAt(),
                product.getStartedAt(),
                product.getClosedAt(),
                message);
    }
}
