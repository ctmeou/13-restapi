package com.ohgiraffers.comprehensive.review.service;

import com.ohgiraffers.comprehensive.common.exception.ConflictException;
import com.ohgiraffers.comprehensive.common.exception.NotFoundException;
import com.ohgiraffers.comprehensive.jwt.CustomUser;
import com.ohgiraffers.comprehensive.member.domain.Member;
import com.ohgiraffers.comprehensive.member.domain.repository.MemberRepository;
import com.ohgiraffers.comprehensive.order.domain.repository.OrderRepository;
import com.ohgiraffers.comprehensive.product.domain.Product;
import com.ohgiraffers.comprehensive.product.domain.repository.ProductRepository;
import com.ohgiraffers.comprehensive.review.domain.Review;
import com.ohgiraffers.comprehensive.review.domain.repository.ReviewRepository;
import com.ohgiraffers.comprehensive.review.dto.request.ReviewCreateRequest;
import com.ohgiraffers.comprehensive.review.dto.response.ReviewResponse;
import com.ohgiraffers.comprehensive.review.dto.response.ReviewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor //의존성 주입할 생성자
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page -1, 5, Sort.by("reviewCode").descending());
    }

    @Transactional(readOnly = true)
    public Page<ReviewsResponse> getReviews(final int page, final Long productCode) {

        final Page<Review> reviews = reviewRepository.findByProductProductCode(getPageable(page), productCode); //연관관계 매핑 시 findByProduct라고 정의해놓으면 Prdouct 엔티티를 전달하고, findByProductProductCode를 작성하면 Product내의 ProductCode를 넘긴다.

        return reviews.map(review -> ReviewsResponse.from(review));

    }

    @Transactional(readOnly = true)
    public ReviewResponse getReview(Long reviewCode) {

        final Review review = reviewRepository.findById(reviewCode)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_REVIEW_CODE));

        return ReviewResponse.from(review);

    }

    @Transactional(readOnly = true)
    public void validateProductOrder(Long productCode, CustomUser customUser) {

        //어떤 사람이 어떤 상품을 구매했는지는 order 테이블을 봐야 한다.
        //exists가 존재하면 true, 존재하지 않으면 false
        if (!orderRepository.existsByProductProductCodeAndMemberCode(productCode, customUser.getMemberCode())) {
            throw new NotFoundException(NOT_FOUND_VALID_ORDER);
        }

    }

    @Transactional(readOnly = true)
    public void validateReviewCreate(Long productCode, CustomUser customUser) {

        //있으면 진행하지 않음
        if (reviewRepository.existsByProductProductCodeAndMemberMemberCode(productCode, customUser.getMemberCode())) {
            throw new ConflictException(ALREADY_EXIST_REVIEW);
        }

    }

    public Long save(ReviewCreateRequest reviewRequest, CustomUser customUser) {

        //연관 관계 매핑이 많이 되어 있기 때문에 찾아서 처리한다.
        //product와 member의 엔티티를 사용하기 위해 의존성 주입을 먼저 한다.
        Product product = productRepository.findById(reviewRequest.getProductCode())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PRODUCT_CODE));

        Member member = memberRepository.findById(customUser.getMemberCode())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER_CODE));

        //리뷰 엔티티 생성 후 save
        final Review newReview = Review.of(
                product,
                member,
                reviewRequest.getReviewTitle(),
                reviewRequest.getReviewContent()
        );

        //비영속 객체였으나 save하면서 반환하면서 reviewCode를 얻게 된다.
        final Review review = reviewRepository.save(newReview);

        return review.getReviewCode();

    }

}
