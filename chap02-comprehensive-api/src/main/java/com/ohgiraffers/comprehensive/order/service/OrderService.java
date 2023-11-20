package com.ohgiraffers.comprehensive.order.service;

import com.ohgiraffers.comprehensive.common.exception.ConflictException;
import com.ohgiraffers.comprehensive.common.exception.NotFoundException;
import com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode;
import com.ohgiraffers.comprehensive.jwt.CustomUser;
import com.ohgiraffers.comprehensive.order.domain.Order;
import com.ohgiraffers.comprehensive.order.domain.repository.OrderRepository;
import com.ohgiraffers.comprehensive.order.dto.request.OrderCreateRequest;
import com.ohgiraffers.comprehensive.order.dto.response.OrderResponse;
import com.ohgiraffers.comprehensive.product.domain.Product;
import com.ohgiraffers.comprehensive.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.NOT_ENOUGH_STOCK;
import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.NOT_FOUND_PRODUCT_CODE;

@Service
@RequiredArgsConstructor
@Transactional //서비스에서는 트랜잭션이 관리되어야 하기 때문에 기본적으로 붙여서 사용한다.
public class OrderService {

    //save한다는 것은 orderRepository를 생성해서 작성
    private final OrderRepository orderRepository; //인터페이스로 생성
    private final ProductRepository productRepository;

    //orderRepository에 전달받은 값을 save한다.
    public void save(OrderCreateRequest orderRequest, CustomUser customUser) {

        //1. 주문 가능 여부를 확인(재고 여부 확인) - product엔티티 필요
        Product product = productRepository.findById(orderRequest.getProductCode())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PRODUCT_CODE));

        /* 주문 가능 여부 확인 */
        if (product.getProductStock() < orderRequest.getOrderAmount()) {
            throw new ConflictException(NOT_ENOUGH_STOCK); //재고를 초과 구매 시 conflictException
        }

        /* 재고 수정 */ //주문 후 실제 재고 변화
        product.updateStock(orderRequest.getOrderAmount());

        final Order newOrder = Order.of(
                product,
                customUser.getMemberCode(),
                orderRequest.getOrderPhone(),
                orderRequest.getOrderEmail(),
                orderRequest.getOrderReceiver(),
                orderRequest.getOrderAddress(),
                orderRequest.getOrderAmount()
        );

        /* 주문 저장 */ //주문 저장 시 order 엔티티 필요
        orderRepository.save(newOrder);
    }

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page -1, 5, Sort.by("orderCode").descending());
    }

    //회원 주문 목록 조회
    @Transactional(readOnly = true) //조회만 되기 때문에 readOnly를 붙여 변화 감지 처리가 일어나지 않도록 한다.
    public Page<OrderResponse> getOrders(Integer page, CustomUser customUser) {

        //엔티티 조회
        Page<Order> orders = orderRepository.findByMemberCode(getPageable(page), customUser.getMemberCode());

        //응답
        return orders.map(order -> OrderResponse.from(order));

    }

}
