package com.ohgiraffers.comprehensive.order.domain.repository;

import com.ohgiraffers.comprehensive.order.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //orderRepository는 상속해줘야 될 대상의 엔티티 필요

    @EntityGraph(attributePaths = { "product" })
    Page<Order> findByMemberCode(Pageable pageable, Long memberCode);

}
