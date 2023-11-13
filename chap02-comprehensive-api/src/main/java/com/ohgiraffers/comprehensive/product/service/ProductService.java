package com.ohgiraffers.comprehensive.product.service;

import com.ohgiraffers.comprehensive.common.exception.BadRequestException;
import com.ohgiraffers.comprehensive.product.dto.response.AdminProductResponse;
import com.ohgiraffers.comprehensive.product.domain.Product;
import com.ohgiraffers.comprehensive.product.domain.repository.ProductRepository;
import com.ohgiraffers.comprehensive.product.dto.response.AdminProductsResponse;
import com.ohgiraffers.comprehensive.product.dto.response.CustomerProductResponse;
import com.ohgiraffers.comprehensive.product.dto.response.CustomerProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.NOT_FOUND_PRODUCT_CODE;
import static com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType.DELETED;
import static com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType.USABLE;

@Service
@RequiredArgsConstructor //반드시 필요한 Constructor을 전달받는 Constructor
public class ProductService {

    //의존성 주입
    private final ProductRepository productRepository;

    private Pageable getPageable(final Integer page) {

        return PageRequest.of(page -1, 10, Sort.by("productCode").descending());

    }

    //⭐ 반환 시 반드시 엔티티가 아닌 로직에서 필요한 응답 클래스로 표현해야 한다.
    /* 1. 상품 목록 조회 : 페이징, 주문 불가 상품 제외(고객) */
    @Transactional(readOnly = true)
    public Page<CustomerProductsResponse> getCustomerProducts(final Integer page) { //바꾸지 못 하게 final 작성

        Page<Product> products = productRepository.findByStatus(getPageable(page), USABLE);

        return products.map(product -> CustomerProductsResponse.from(product));

    }

    /* 2. 상품 목록 조회 : 페이징, 주문 불가 상품 포함(관리자) */
    @Transactional(readOnly = true)
    public Page<AdminProductsResponse> getAdminProducts(final Integer page) { //바꾸지 못 하게 final 작성

        Page<Product> products = productRepository.findByStatusNot(getPageable(page), DELETED);

        return products.map(product -> AdminProductsResponse.from(product));

    }

    /* 3. 상품 목록 조회 - 카테고리 기준, 페이징, 주문 불가 상품 제외(고객) */
    //카테고리 기반으로 가져온다.
    @Transactional(readOnly = true)
    public Page<CustomerProductsResponse> getCustomerProductsByCategory(final Integer page, final Long categoryCode) { //바꾸지 못 하게 final 작성

        Page<Product> products = productRepository.findByCategoryCategoryCodeAndStatus(getPageable(page), categoryCode, USABLE);

        return products.map(product -> CustomerProductsResponse.from(product));

    }

    /* 4. 상품 목록 조회 - 상품명 검색 기준, 페이징, 주문 불가 상품 제외(고객) */
    @Transactional(readOnly = true)
    public Page<CustomerProductsResponse> getCustomerProductsByProductName(final Integer page, final String productName) { //바꾸지 못 하게 final 작성

        Page<Product> products = productRepository.findByProductNameContainsAndStatus(getPageable(page), productName, USABLE);

        return products.map(product -> CustomerProductsResponse.from(product));

    }

    //⭐ nullPointException을 방지하기 위해 Optional하게 이용하는 것이 좋고 어떻게 사용할지 정의
    /* 5. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 제외(고객) */
    @Transactional(readOnly = true)                           //조회 조건
    public CustomerProductResponse getCustomerProduct(final Long productCode) {

        Product product = productRepository.findByProductCodeAndStatus(productCode, USABLE)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_PRODUCT_CODE)); //조회했는데 null이면 exception 처리
                //찾아지지 않을 경우 코드 진행            //현재 상황에 맞는 exceptionCode를 넘긴다.

        return CustomerProductResponse.from(product);

    }

    /* 6. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 포함(관리자) */
    @Transactional(readOnly = true)
    public AdminProductResponse getAdminProduct(final Long productCode) {

        Product product = productRepository.findByProductCodeAndStatusNot(productCode, DELETED) //DELETED 빼고 조회
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_PRODUCT_CODE));

        return AdminProductResponse.from(product);

    }

}
