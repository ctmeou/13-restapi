package com.ohgiraffers.comprehensive.review.presentation;

import com.ohgiraffers.comprehensive.common.paging.Pagenation;
import com.ohgiraffers.comprehensive.common.paging.PagingButtonInfo;
import com.ohgiraffers.comprehensive.common.paging.PagingResponse;
import com.ohgiraffers.comprehensive.order.dto.response.OrderResponse;
import com.ohgiraffers.comprehensive.review.dto.response.ReviewsResponse;
import com.ohgiraffers.comprehensive.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /* 1. 상품별 리뷰 목록 조회 */
    @GetMapping("/reviews/product/{productCode}") //특정 상품에 대한 리뷰 조회
    public ResponseEntity<PagingResponse> getReviews(
            @PathVariable final Long productCode,
            @RequestParam(defaultValue = "1") final int page
    ) {

        final Page<ReviewsResponse> reviews = reviewService.getReviews(page, productCode); //조회에 필요한 조건을 넘기면서 요청을 한다.
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(reviews);
        final PagingResponse pagingResponse = PagingResponse.of(reviews.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);

    }

}
