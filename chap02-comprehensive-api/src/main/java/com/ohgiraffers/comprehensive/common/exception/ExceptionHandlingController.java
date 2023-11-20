package com.ohgiraffers.comprehensive.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //@RestController, @Controller의 차이 RestController는 ResponseBody가 기본적으로 붙어있다.
//@RestControllerAdvice : 응답값이 곧 응답 메시지가 된다.
public class ExceptionHandlingController {

    //200번대는 성공 400번대는 클라이언트 에러 500번대는 서버 에러
    /* 400 : Bad Request */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestException(BadRequestException e) {

        final ExceptionResponse exceptionResponse
                = ExceptionResponse.of(e.getCode(), e.getMessage());

        return ResponseEntity.badRequest().body(exceptionResponse); //body에 정의한 exception Code를 정의
        //exceptionResponse를 body에 넣어주면 만들어진 400번 코드와 code와 message가 body 영역으로 넘어간다.

    }

    /* 401 : UnAuthorized 인증 오류 => 이미 처리되어 있음 */
    //인증 오류는 jwt-JwtAuthenticationEntityPoint 보면 JWT에서 인증이 안된 경우와 login-LoginFailureHandler 보면 로그인 실패한 계정일 경우 정의해뒀기 때문에 여기서 작성하지 않아도 된다.

    /* 403 : Forbidden 인가 오류 => 이미 처리되어 있음 */
    //JWT-jwtAccessDeniedHandler에서 권한이 없는데 접근하려고 할 때 정의해뒀기 때문에 여기서 작성하지 않아도 된다.

    /* 404 : Not found */
    //클라이언트에서 요청이 잘못되었을 경우(다른 형태의 파라미터를 넘길 경우, 요청에 대한 형식이 틀린 것은 없지만 리소스가 없을 경우 등)
    //NotFoundException 정의 후 기반으로 메소드 정의
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundException(NotFoundException e) {

        final ExceptionResponse exceptionResponse
                = ExceptionResponse.of(e.getCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse); //notFound는 body를 바로 작성할 수 없어 status를 작성한다.

    }

    /* 409 : Conflict => 충돌. 논리적으로 해당 기능을 수행할 수 없는 경우 처리 */ //클라이언트가 서버로 요청을 보냈을 때, 요청, 인증, 인가는 잘 됐으나 논리적으로 수행할 수 없을 경우(주문하려는 수량이 재고 초과 시)
    //ConflictException 정의 후 기반으로 메소드 정의
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionResponse> conflictException(ConflictException e) {

        final ExceptionResponse exceptionResponse
                = ExceptionResponse.of(e.getCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);

    }

    /* 500 : Server Errors */
    @ExceptionHandler(ServerInternalException.class)
    public ResponseEntity<ExceptionResponse> serverInternalException(ServerInternalException e) {

        final ExceptionResponse exceptionResponse
                = ExceptionResponse.of(e.getCode(), e.getMessage());

        return ResponseEntity.internalServerError().body(exceptionResponse);

    }

    /* Validation Exception */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodValidException(MethodArgumentNotValidException e) {

        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();

        final ExceptionResponse exceptionResponse = ExceptionResponse.of(9000, defaultMessage);

        return ResponseEntity.badRequest().body(exceptionResponse);

    }

}
