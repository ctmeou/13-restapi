package com.ohgiraffers.comprehensive.product.presentation;
import com.ohgiraffers.comprehensive.common.paging.Pagenation;
import com.ohgiraffers.comprehensive.common.paging.PagingButtonInfo;
import com.ohgiraffers.comprehensive.common.paging.PagingResponse;
import com.ohgiraffers.comprehensive.product.dto.response.AdminProductsResponse;
import com.ohgiraffers.comprehensive.product.dto.response.CustomerProductsResponse;
import com.ohgiraffers.comprehensive.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    /* 1. 상품 목록 죄회 - 페이징, 주문 불가 상품 제외(고객) */
    @GetMapping("/products") //모든 상품을 고객 입장에서 조회한다.         조회된 페이지를 넘기기 위해 파라미터 생성
    public ResponseEntity<PagingResponse> getCustomerProducts(@RequestParam(defaultValue = "1") final Integer page) {

                                                                //getCustomerProducts는 몇 번째 페이지를 넘길지 설정한다.
        final Page<CustomerProductsResponse> products = productService.getCustomerProducts(page); //조회된 내용 넘긴다. products에 대한 데이터
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(products);
        final PagingResponse pagingResponse = PagingResponse.of(products.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);

    }

    /* 2. 상품 목록 죄회 - 페이징, 주문 불가 상품 포함(관리자) */
    @GetMapping("/products-management")
    public ResponseEntity<PagingResponse> getAdminProducts(@RequestParam(defaultValue = "1") final Integer page) {

        final Page<AdminProductsResponse> products = productService.getAdminProducts(page);
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(products);
        final PagingResponse pagingResponse = PagingResponse.of(products.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);

    }

    /* 3. 상품 목록 조회 - 카테고리 기준, 페이징, 주문 불가 상품 제외(고객) */
    @GetMapping("/products/categories/{categoryCode}") //카데고리 기준으로 조회를 하되 카데고리 코드를 기준으로 조회한다.
    public ResponseEntity<PagingResponse> getCustomerProductsByCategory(
            @RequestParam(defaultValue = "1") final Integer page, @PathVariable final Long categoryCode) {

        final Page<CustomerProductsResponse> products = productService.getCustomerProductsByCategory(page, categoryCode);
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(products);
        final PagingResponse pagingResponse = PagingResponse.of(products.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);

    }

    /* 4. 상품 목록 조회 - 상품명 검색 기준, 페이징, 주문 불가 상품 제외(고객) */
    @GetMapping("/products/search")
    public ResponseEntity<PagingResponse> getCustomerProductsByProductName(
            @RequestParam(defaultValue = "1") final Integer page, @RequestParam final String productName) {

        final Page<CustomerProductsResponse> products = productService.getCustomerProductsByProductName(page, productName);
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(products);
        final PagingResponse pagingResponse = PagingResponse.of(products.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);

    }


}
