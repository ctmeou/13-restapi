package com.ohgiraffers.comprehensive.review.domain;

import com.ohgiraffers.comprehensive.common.domain.BaseEntity;
import com.ohgiraffers.comprehensive.member.domain.Member;
import com.ohgiraffers.comprehensive.product.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "tbl_review")
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "status = 'USABLE'") //활성화된 부분만 조회(삭제되지 않은 부분)
//@SQLDelete(sql = "UPDATE tbl_review SET status = 'DELETED' WHERE reviewCode = ?")
public class Review extends BaseEntity { //나머지는 상속받은 BaseEntity에서 오기 때문에 직접적으로 명시할 필요가 없다.(공통적으로 사용할 created_at, modified_at, status은 extends로 사용)

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long reviewCode;

    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "memberCode")
    private Member member; //리뷰작성자를 알기 위해 연관 관계 매핑

    @Column(nullable = false)
    private String reviewTitle;

    @Column(nullable = false)
    private String reviewContent;

    public Review(Product product, Member member, String reviewTitle, String reviewContent) {
        this.product = product;
        this.member = member;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
    }

    public static Review of(Product product, Member member, String reviewTitle, String reviewContent) {

        return new Review(
                product,
                member,
                reviewTitle,
                reviewContent
        );

    }

}