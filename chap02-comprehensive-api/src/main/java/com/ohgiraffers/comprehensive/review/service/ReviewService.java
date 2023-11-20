package com.ohgiraffers.comprehensive.review.service;

import com.ohgiraffers.comprehensive.review.domain.Review;
import com.ohgiraffers.comprehensive.review.domain.repository.ReviewRepository;
import com.ohgiraffers.comprehensive.review.dto.response.ReviewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor //의존성 주입할 생성자
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page -1, 5, Sort.by("reviewCode").descending());
    }

    @Transactional(readOnly = true)
    public Page<ReviewsResponse> getReviews(final int page, final Long productCode) {

        final Page<Review> reviews = reviewRepository.findByProductProductCode(getPageable(page), productCode); //연관관계 매핑 시 findByProduct라고 정의해놓으면 Prdouct 엔티티를 전달하고, findByProductProductCode를 작성하면 Product내의 ProductCode를 넘긴다.

        return reviews.map(review -> ReviewsResponse.from(review));

    }

}
