package com.ohgiraffers.comprehensive.order.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
public class OrderCreateRequest {

    //insert하기 위해 클라이언트한테 받아오려는 정보
    @Min(value = 1)
    private final Long productCode;
    @NotNull
    private final String orderPhone;
    @NotBlank
    private final String orderEmail;
    @NotNull
    private final String orderReceiver;
    @NotBlank
    private final String orderAddress;
    @Min(value = 1)
    private final Long orderAmount;

}
