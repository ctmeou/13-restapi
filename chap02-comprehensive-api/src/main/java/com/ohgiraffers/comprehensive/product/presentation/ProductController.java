package com.ohgiraffers.comprehensive.product.presentation;
import com.ohgiraffers.comprehensive.common.paging.Pagenation;
import com.ohgiraffers.comprehensive.common.paging.PagingButtonInfo;
import com.ohgiraffers.comprehensive.common.paging.PagingResponse;
import com.ohgiraffers.comprehensive.product.dto.request.ProductCreateRequest;
import com.ohgiraffers.comprehensive.product.dto.request.ProductUpdateRequest;
import com.ohgiraffers.comprehensive.product.dto.response.AdminProductResponse;
import com.ohgiraffers.comprehensive.product.dto.response.AdminProductsResponse;
import com.ohgiraffers.comprehensive.product.dto.response.CustomerProductResponse;
import com.ohgiraffers.comprehensive.product.dto.response.CustomerProductsResponse;
import com.ohgiraffers.comprehensive.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    //⭐ url 부분 잘 작성하고, {categoryCode}로 받아오는 것은 PathVariable을 이용한다.
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

    /* 5. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 제외(고객) */
    @GetMapping("/products/{productCode}")
    public ResponseEntity<CustomerProductResponse> getCustomerProduct(@PathVariable final Long productCode) { //@PathVariable final Long productCode로 받은 값을 전달하면서 요청

        final CustomerProductResponse customerProductResponse = productService.getCustomerProduct(productCode);

        return ResponseEntity.ok(customerProductResponse);

    }

    /* 6. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 포함(관리자) */
    @GetMapping("/products-management/{productCode}")
    public ResponseEntity<AdminProductResponse> getAdminProduct(@PathVariable final Long productCode) {

        final AdminProductResponse adminProductResponse = productService.getAdminProduct(productCode);

        return ResponseEntity.ok(adminProductResponse);

    }

    /* 7. 상품 등록(관리자) */
    //⭐ post 방식일 경우 생성된 컨텐츠에 접근하려면 어떤 주소에 접근해야 하는지 규칙
    @PostMapping("/products")           //@Valid을 작성하면 ProductCreateRequest productRequest 넘어올 때 ProductCreateRequest에서 작성한 어노테이션들이 검증돼서 넘어온다.
    public ResponseEntity<Void> save(@RequestPart @Valid final ProductCreateRequest productRequest,
                                     @RequestPart final MultipartFile productImg) {

        final Long productCode = productService.save(productImg, productRequest); //저장된 productCode가 몇 번인지 필요하다.

        //응답 시 등록 후 created 코드를 보내야 한다.(201번 코드)
        return ResponseEntity.created(URI.create("/products-management/1" + productCode)).build(); //지금 생성한 상품에 대해 발생한 상품 코드를 넘겨야 한다.

    }

    /* 상품 수정(관리자) */
    @PutMapping("/products/{productCode}") //요청 put 방식
    public ResponseEntity<Void> update(@PathVariable final Long productCode,
                                       @RequestPart @Valid final ProductUpdateRequest productRequest, //업데이트하고자 하는 request가 있을 경우 처리한다.
                                       @RequestPart(required = false) final MultipartFile multipartFile) { //required = false 사용해서 이미지가 넘어올 수도 있고 안 넘어올 수도 있다.

        return ResponseEntity.created(URI.create("/products-management" + productCode)).build();

    }



}
