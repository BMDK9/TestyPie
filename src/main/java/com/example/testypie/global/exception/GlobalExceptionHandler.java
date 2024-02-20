package com.example.testypie.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Controller 내에서 발생하는 Exception에 대해 Catch해서 클라이언트에게 에러 응답을 보내주는 기능을 수행 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage(), e.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body("Server error!");
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("*************************Custom Error 발생***********************************");
        log.error(String.valueOf(e.errorCode));
        log.error("****************************************************************************");

        HttpStatus status = HttpStatus.valueOf(e.getErrorCode().getStatus());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(status).body(errorResponse);
    }

    // ErrorCode를 보유한 예외 클래스 정의
    public static class CustomException extends RuntimeException {
        private final ErrorCode errorCode;

        public CustomException(ErrorCode errorCode) {
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }
    }
}
