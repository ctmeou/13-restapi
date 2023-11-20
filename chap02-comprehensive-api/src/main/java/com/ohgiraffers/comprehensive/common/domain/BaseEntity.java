package com.ohgiraffers.comprehensive.common.domain;

import com.ohgiraffers.comprehensive.common.domain.type.StatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static com.ohgiraffers.comprehensive.common.domain.type.StatusType.USABLE;
import static javax.persistence.EnumType.STRING;

@Getter
@MappedSuperclass //엔티티의 상위 클래스
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity { //abstract : 추상클래스 : 객체로 생성하지 못 하도록 만든다.

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private StatusType status = USABLE;

}