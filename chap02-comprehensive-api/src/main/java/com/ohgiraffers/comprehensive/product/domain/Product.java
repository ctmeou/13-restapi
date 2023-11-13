package com.ohgiraffers.comprehensive.product.domain;

import com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


import static com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType.USABLE;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "tbl_product")
@NoArgsConstructor(access = PROTECTED) //기본 생성자, 접근에 대한 것을 지정할 수 있다.
@Getter
@EntityListeners(AuditingEntityListener.class) //@EntityListeners : 엔티티의 변화를 감지한다.
public class Product {

    @Id
    private Long productCode;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long productPrice;

    @Column(nullable = false)
    private String productDescription;

    //엔티티 참조
    @ManyToOne(fetch = FetchType.LAZY) //다중성 어노테이션, fetch = FetchType.LAZY : 필요 시에 조회한다.
    @JoinColumn(name = "categoryCode") //FK의 이름 작성
    private Category category;

    @Column(nullable = false)
    private String productImageUrl;

    @Column(nullable = false)
    private Long productStock;

    //@CreatedDate, @LastModifiedDate : JDA에서 감지해 해당 행이 수정되는 순간 자동으로 처리해준다. = 컬럼을 추가적으로 다루지 않아도 된다.
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @Enumerated(value = STRING) //enum 문자열로 사용 시 type 설정해줘야 한다.
    @Column(nullable = false)
    private ProductStatusType status = USABLE;

}
