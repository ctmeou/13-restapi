package com.ohgiraffers.comprehensive.review.domain.repository;

import com.ohgiraffers.comprehensive.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = { "product", "member" }) //필드명을 작성하면 되고, pathJoin을 이용해서 여러 개의 값을 한 번에 조회해온다.
    Page<Review> findByProductProductCode(Pageable pageable, Long productCode);

    boolean existsByProductProductCodeAndMemberMemberCode(Long productCode, Long memberCode);
}
