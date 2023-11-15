package com.ohgiraffers.comprehensive.product.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

//ENUM 타입으로 설정
public enum ProductStatusType {

            //요청으로 받고 싶은 값
    USABLE("usable"),
    DISABLE("disable"),
    DELETED("deleted");

    private final String value;

    ProductStatusType(String value) { this.value = value; }

    @JsonCreator
    public static ProductStatusType from(String value) {

        for (ProductStatusType status : ProductStatusType.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }

        return null;

    }

    @JsonValue //@JsonValue 작성 시 USABLE("usable")에 작성해놓은 값 중 usable로 값이 넘어온다. 미작성 시 USABLE
    public String getValue() { return value; }

}
