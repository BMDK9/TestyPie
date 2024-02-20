package com.example.testypie.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private int statusCode; // 에러 상태 코드
    private String errorMessage; // 에러 구분 코드

    /**
     * ErrorResponse 생성자 1
     *
     * @param code ErrorCode
     */
    @Builder
    public ErrorResponse(final ErrorCode code) {
        this.statusCode = code.getStatus();
        this.errorMessage = code.getMessage();
    }

    /**
     * Global Exception 전송 타입 -2
     *
     * @param code ErrorCode
     * @return ErrorResponse
     */
    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }
}
