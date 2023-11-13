package com.ohgiraffers.comprehensive.product.dto.response;

import com.ohgiraffers.comprehensive.product.domain.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class CustomerProductsResponse {

    private final Long productCode;
    private final String productName;
    private final Long productPrice;
    private final String productImageUrl;

    public static CustomerProductsResponse from(final Product product) { //엔티티 전달했을 때 아래의 정보를 가져갈 수 있다.(모든 정보가 아닌 필요한 정보)

        return new CustomerProductsResponse(
                product.getProductCode(),
                product.getProductName(),
                product.getProductPrice(),
                product.getProductImageUrl()
        );

    }

}
