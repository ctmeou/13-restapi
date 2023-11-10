package com.ohgiraffers.comprehensiveapi.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_category")
@NoArgsConstructor
@Getter
public class Category {

    @Id //PK
    private Long categoryCode;

    @Column(nullable = false) //생략해도 된다(네이밍 일치), 속성을 사용할 때는 어노테이션 작성해야 한다.
    private String categoryName;


}
