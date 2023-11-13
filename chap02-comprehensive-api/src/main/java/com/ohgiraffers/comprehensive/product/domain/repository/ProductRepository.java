package com.ohgiraffers.comprehensive.product.domain.repository;

import com.ohgiraffers.comprehensive.product.domain.Product;
import com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//기본적인 CRUD, SORTING 기능이 정의되어 있어 호출해서 사용하면 되고 추가적인 부분은 쿼리메소드를 이용하면 된다.
//쿼리 메소드가 길면 JPQL을 이용하면 된다. 추가적인 것이 필요하면 Native Query를 쓰면 된다.
/* 연관 관계 매핑 시 연관 대상 entity의 PK별로 한 번씩 구문이 발생하는 N + 1 문제가 있다.
* 해당 필드 미사용 : FetchType.LAZY -> 아예 조회하지 않음
* 해당 필드 사용 : fetch join(JPQL), @EntityGrap(Query Method) -> join해서 가져옴 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /* 1. 상품 목록 조회 : 페이징, 주문 불가 상품 제외(고객) */
    Page<Product> findByStatus(Pageable pageable, ProductStatusType productStatusType); //페이지 타입으로 반환, 조회 조건이 status이면 findByStatus로 처리

    /* 2. 상품 목록 조회 : 페이징, 주문 불가 상품 포함(관리자) */
    @EntityGraph(attributePaths = {"category"}) //엔티티 입장에서 join 하고 싶은 필드명을 작성한다.
    Page<Product> findByStatusNot(Pageable pageable, ProductStatusType productStatusType); //전달하지 않은 것에 대한 쿼리 메소드는 조건 뒤에 Not을 붙이면 된다. findByStatusNot

    /* 3. 상품 목록 조회 - 카테고리 기준, 페이징, 주문 불가 상품 제외(고객) */
    //findByCategoryCategoryCodeAndStatus 카테고리 내의 카테고리 코드와 판매 상태에 대해 조회
    Page<Product> findByCategoryCategoryCodeAndStatus(Pageable pageable, Long categoryCode, ProductStatusType productStatusType);

    /* 4. 상품 목록 조회 - 상품명 검색 기준, 페이징, 주문 불가 상품 제외(고객) */
    //findByProductNameContains 이름이 전부 포함된 것
    Page<Product> findByProductNameContainsAndStatus(Pageable pageable, String productName, ProductStatusType productStatusType);

    /* 5. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 제외(고객) */
    Optional<Product> findByProductCodeAndStatus(Long productCode, ProductStatusType productStatusType);

    /* 6. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 포함(관리자) */
    Optional<Product> findByProductCodeAndStatusNot(Long productCode, ProductStatusType productStatusType);

}
