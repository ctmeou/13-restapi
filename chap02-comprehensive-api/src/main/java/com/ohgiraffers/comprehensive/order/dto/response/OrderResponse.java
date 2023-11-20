package com.ohgiraffers.comprehensive.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ohgiraffers.comprehensive.order.domain.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

//화면에서 어떤 값을 써야 하는지 판단해서 작성
@Getter
@RequiredArgsConstructor
public class OrderResponse {

    private final Long orderCode;
    private final Long productCode;
    private final String productName;
    private final Long productPrice;
    private final Long orderAmount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime orderDate;

    public static OrderResponse from(Order order) {

        return new OrderResponse(
                //전달하려는 값 order 엔티티에서 꺼내서 전달
                order.getOrderCode(),
                order.getProduct().getProductCode(),
                order.getProduct().getProductName(),
                order.getProduct().getProductPrice(),
                order.getOrderAmount(),
                order.getOrderDate()
        );

    }

}
