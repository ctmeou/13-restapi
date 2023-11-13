package com.ohgiraffers.comprehensive.product.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
public class ProductCreateRequest {

    //테이블 생성 시 insert 해야 할 코드(클라이언트한테 요청했을 때 받을 값만 작성)
    //데이터 검증 어노테이션 추가 작성(공백이 아니어야 하고 값이 최소 1이어야 한다.)
    @NotBlank
    private final String productName;
    @Min(value = 1)
    private final Long productPrice;
    @NotBlank
    private final String productDescription;
    @Min(value = 1)
    private final Long categoryCode;
    @Min(value = 1)
    private final Long productStock;

}
