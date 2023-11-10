package com.ohgiraffers.restapi.section03.valid;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@Getter @Setter @ToString
public class UserDTO {

    private int no;
    @NotNull(message = "아이디는 반드시 입력되어야 합니다.") //null은 안되나 빈문자열이나 공백의 문자열은 가능하다.(null x, "", " ")
    @NotBlank(message = "아이디는 공백일 수 없습니다.") //위의 사항 전부 되지 않는다.
    private String id; //의도에 따라 제한을 줄 수 있다.
    private String pwd;
    @NotNull(message = "이름은 반드시 입력되어야 합니다.")
    @Size(min = 2, message = "이름은 2글자 이상이어야 합니다.") //min, max 속성을 이용해서 사이즈를 지정할 수 있다. 또는 어노테이션 자체로 사용할 수도 있다.(@Min, @Max)
    private String name;
    @Past //@Past 지금 시스템 시간보다 과거 시간, @Future 지금 시스템 시간보다 미래 시간
    private Date enrollDate;

}
