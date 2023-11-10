package com.ohgiraffers.comprehensiveapi.product.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_product")
public class Product {

    @Id
    private Long productCode;

    private String productName;

    private Long productPrice;

    private String productDescription;

    //엔티티 참조
    @ManyToOne //다중성 어노테이션
    @JoinColumn(name = "categoryCode") //FK의 이름 작성
    private Category category;

    private String productImageUrl;

    private Long productStock;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
