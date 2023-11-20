package com.ohgiraffers.comprehensive.order.presentation;

import com.ohgiraffers.comprehensive.common.paging.Pagenation;
import com.ohgiraffers.comprehensive.common.paging.PagingButtonInfo;
import com.ohgiraffers.comprehensive.common.paging.PagingResponse;
import com.ohgiraffers.comprehensive.jwt.CustomUser;
import com.ohgiraffers.comprehensive.order.dto.request.OrderCreateRequest;
import com.ohgiraffers.comprehensive.order.dto.response.OrderResponse;
import com.ohgiraffers.comprehensive.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /* 1. 주문 등록 */
    @PostMapping("/order") //save는 클라이언트한테 저장하려는 정보가 필요(매개변수가 넘어와야 한다.)
    public ResponseEntity<Void> save(@RequestBody @Valid final OrderCreateRequest orderRequest,
                                     @AuthenticationPrincipal final CustomUser customUser) { //회원 정보(JWT saveAuthentication의 정보를 가지고 있으나 ID를 꺼내 memberCode를 꺼내서 비교하는 것이 번거롭다. 따라서 자주 사용하는 것을 담아놓도록 한다.(jwt-customUser)

        orderService.save(orderRequest, customUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /* 2. 회원의 주문 목록 조회 */
    @GetMapping("/order")
    public ResponseEntity<PagingResponse> getOrders(
            //주문 목록과 어떤 회원인지에 대한 정보도 넘어와 처리한다. 두 개의 값을 기반으로 조회 요청을 한다.
            @RequestParam(defaultValue = "1") final Integer page,
            @AuthenticationPrincipal CustomUser customUser
    ) {

        final Page<OrderResponse> orders = orderService.getOrders(page, customUser); //원하는 페이지와 customUser를 전달하면서 메소드 호출
        //결과는 OrderResponse의 페이지가 필요하다.
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(orders);
        final PagingResponse pagingResponse = PagingResponse.of(orders.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse); //ok와 함께 조회된 내역을 보여준다.

    }

}
