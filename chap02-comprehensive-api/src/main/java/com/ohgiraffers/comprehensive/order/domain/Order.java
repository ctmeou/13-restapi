package com.ohgiraffers.comprehensive.order.domain;

import com.ohgiraffers.comprehensive.product.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "tbl_order")
@NoArgsConstructor(access = PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long orderCode;

    @ManyToOne(cascade = CascadeType.PERSIST) //하나의 product당 여러 개의 주문이 발생할 수 있다(주문 Many, product one)
    @JoinColumn(name = "productCode") //order 테이블에 있는 FK의 값을 작성해줘야 한다.
    private Product product;

    @Column(nullable = false)
    private Long memberCode;

    @Column(nullable = false)
    private String orderPhone;

    @Column(nullable = false)
    private String orderEmail;

    @Column(nullable = false)
    private String orderReceiver;

    @Column(nullable = false)
    private String orderAddress;

    @Column(nullable = false)
    private Long orderAmount;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    public Order(Product product, Long memberCode, String orderPhone, String orderEmail, String orderReceiver, String orderAddress, Long orderAmount) {
        this.product = product;
        this.memberCode = memberCode;
        this.orderPhone = orderPhone;
        this.orderEmail = orderEmail;
        this.orderReceiver = orderReceiver;
        this.orderAddress = orderAddress;
        this.orderAmount = orderAmount;
    }
    //값들에 대해서 생성자에게 setting
    public static Order of(Product product, Long memberCode, String orderPhone, String orderEmail, String orderReceiver, String orderAddress, Long orderAmount) {

        return new Order(
                product, memberCode, orderPhone, orderEmail, orderReceiver, orderAddress, orderAmount
        );

    }

}
