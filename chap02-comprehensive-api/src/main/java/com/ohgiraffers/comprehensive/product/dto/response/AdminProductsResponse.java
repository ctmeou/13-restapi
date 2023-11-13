package com.ohgiraffers.comprehensive.product.dto.response;

import com.ohgiraffers.comprehensive.product.domain.Product;
import com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class AdminProductsResponse {

    private final Long productCode;
    private final String productName;
    private final Long productPrice;
    private final String categoryName;
    private final Long productStock;
    private final ProductStatusType status;

    public static AdminProductsResponse from(final Product product) { //엔티티 전달했을 때 아래의 정보를 가져갈 수 있다.(모든 정보가 아닌 필요한 정보)

        return new AdminProductsResponse(
                product.getProductCode(),
                product.getProductName(),
                product.getProductPrice(),
                product.getCategory().getCategoryName(),
                product.getProductStock(),
                product.getStatus()
        );

    }

}
