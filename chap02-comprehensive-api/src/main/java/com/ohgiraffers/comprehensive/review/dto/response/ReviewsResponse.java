package com.ohgiraffers.comprehensive.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ohgiraffers.comprehensive.review.domain.Review;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReviewsResponse {

    //화면에서 사용하는 값만 사용
    private final Long reviewCode;
    private final String productName;
    private final String memberName;
    private final String reviewTitle;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    public static ReviewsResponse from(Review review) {

        //위의 값을 생성자로 전달
        return new ReviewsResponse(
                review.getReviewCode(),
                review.getProduct().getProductName(),
                review.getMember().getMemberName(),
                review.getReviewTitle(),
                review.getCreatedAt()
        );

    }

}
