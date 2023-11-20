package com.ohgiraffers.comprehensive.review.presentation;

import com.ohgiraffers.comprehensive.common.paging.Pagenation;
import com.ohgiraffers.comprehensive.common.paging.PagingButtonInfo;
import com.ohgiraffers.comprehensive.common.paging.PagingResponse;
import com.ohgiraffers.comprehensive.jwt.CustomUser;
import com.ohgiraffers.comprehensive.review.dto.request.ReviewCreateRequest;
import com.ohgiraffers.comprehensive.review.dto.response.ReviewResponse;
import com.ohgiraffers.comprehensive.review.dto.response.ReviewsResponse;
import com.ohgiraffers.comprehensive.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

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

    /* 2. 리뷰 코드로 리뷰 상세 조회 */
    @GetMapping("/reviews/{reviewCode}") //조회이기 때문에 getMapping
    public ResponseEntity<ReviewResponse> getReview(@PathVariable final Long reviewCode) {

        final ReviewResponse reviewResponse = reviewService.getReview(reviewCode);

        return ResponseEntity.ok(reviewResponse);

    }

    /* 3. 리뷰 작성 */
    @PostMapping("/reviews")    //json 문자열            클래스 명칭
    public ResponseEntity<Void> save(@RequestBody @Valid ReviewCreateRequest reviewRequest,
                                     @AuthenticationPrincipal CustomUser customUser) { //누가 작성하는지에 대한 정보

        /* 해당 상품 구매 여부 확인 */
        reviewService.validateProductOrder(reviewRequest.getProductCode(), customUser);
        //구매한 상품이 없으면 throw에 걸려서 에러 발생

        /* 리뷰 미작성 확인 */
        reviewService.validateReviewCreate(reviewRequest.getProductCode(), customUser);
        //리뷰 테이블에 그 사람이 작성한 리뷰가 있는지 확인

        /* 리뷰 저장 */
        //실제 DB에 저장한다. 발생한 reviewCode를 저장한다.
        Long reviewCode = reviewService.save(reviewRequest, customUser);

        return ResponseEntity.created(URI.create("/api/v1/reviews/" + reviewCode)).build();

    }

}
