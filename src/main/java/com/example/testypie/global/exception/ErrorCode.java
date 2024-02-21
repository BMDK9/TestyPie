package com.example.testypie.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;

@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum ErrorCode {
    /**
     * ***** Global Error CodeList ***** HTTP Status Code 400 : Bad Request 잘못된 요청 401 : Unauthorized
     * 인증되지 않은 사용자 403 : Forbidden 접근권한 없음 404 : Not Found Resource를 찾을수 없음 409 : Conflict 데이터 중복 422
     * : Unprocessable Entity 요청은 올바르나 파일을 읽을 수 없음 500 : INTERNAL SERVER 서버 에러
     */

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_INPUT_VALUE(400, "입력값이 올바르지 않습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),

    /* 500 INTERNAL_SERVER_ERROR */
    SERVER_ERROR(500, "예기치 못한 오류가 발생하였습니다."),

    /* Bugreport */
    SELECT_BUGREPORT_NOT_FOUND(404, "해당 버그 리포트는 존재하지 않습니다."),
    SELECT_BUGREPORT_INVALID(403, "해당 작성자만 접근할 수 있습니다."),

    /* Category */
    SELECT_CATEGORY_NOT_FOUND(404, "해당 카테고리는 존재하지 않습니다."),
    SELECT_CATEGORY_NOT_MATCH(404, "존재하지 않는 상위 카테고리 입니다."),

    /* Comment(끝) */
    SELECT_COMMENT_NOT_EXIST(404, "해당 댓글은 존재하지 않습니다."),
    SELECT_COMMENT_INVALID_USER(403, "해당 댓글의 작성자만 접근할 수 있습니다."),
    SELECT_COMMENT_NOT_MATCH_ORIGIN(409, "요청한 댓글의 위치가 존재하지 않습니다."),

    /* USER(user) */
    SIGNUP_DUPLICATED_USER_ACCOUNT(409, "이미 존재하는 아이디입니다."),
    SIGNUP_DUPLICATED_USER_NICKNAME(409, "이미 존재하는 닉네임입니다."),
    SIGNUP_DUPLICATED_USER_EMAIL(409, "이미 존재하는 이메일입니다."),
    LOGIN_INVALID_ACCOUNT(401, "올바르지 않은 아이디로 로그인을 시도하셨습니다."),
    LOGIN_INVALID_PASSWORD(401, "올바르지 않은 비밀번호로 로그인을 시도하셨습니다."),
    UPDATE_IDENTICAL_EMAIL(409, "이전과 동일한 이메일을 입력하셨습니다."),
    SELECT_USER_NOT_FOUND(404, "찾으시는 회원은 존재하지 않습니다."),
    DELETE_USER_NOT_FOUND(404, "삭제하시려는 회원은 존재하지 않습니다."),
    UPDATE_USER_INVALID_AUTHORIZATION(403, "해당 회원의 수정 권한이 없습니다."),
    DELETE_USER_INVALID_AUTHORIZATION(403, "해당 회원의 삭제 권한이 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(401, "유효한 토큰이 아닙니다."),
    REFRESH_TOKEN_INVALID(401, "유효한 토큰이 아닙니다."),

    /* Product */

    /* PROFILE */
    UPDATE_PROFILE_BAD_REQUEST(400, "프로필 업데이트가 실패했습니다."),
    SELECT_PROFILE_USER_NOT_FOUND(401, "해당 회원이 없습니다."),
    PROFILE_USER_INVALID_AUTHORIZATION(403, "해당 유저에게 권한이 업습니다."),
    PROFILE_PRODUCT_ID_NOT_FOUND(404, "해당 product는 존재하지 않습니다."),
    PROFILE_FEEDBACK_ID_NOT_FOUND(404, "해당 피드백은 존재하지 않습니다."),
    PROFILE_PRODUCT_FEEDBACK_ID_NOT_FOUND(404, "해당 product나 피드백은 찾을 수 없습니다."),
    PROFILE_AVERAGE_FEEDBACK_RATING_NULL(404, "한번도 평가받지 않으면 확인할수 없습니다."),

    /* CommentLike */

    /* Feedback(끝) */
    SELECT_FEEDBACK_NOT_FOUND(404, "찾으시는 피드백이 존재하지 않습니다."),
    CREATE_FEEDBACK_NOT_ALLOWED(403, "Product작성자는 피드백을 제출할 수 없습니다."),
    CREATE_FEEDBACK_ALREADY_SUBMITTED(403, "이미 피드백을 제출했습니다."),
    CREATE_FEEDBACK_PASSED_DUE_DATE(403, "이미 만료된 product입니다."),

    /* Product(끝) */
    SELECT_PRODUCT_NOT_FOUND(404, "찾으시는 product는 존재하지 않습니다."),
    SELECT_PRODUCT_INVALID_AUTHORIZATION(403, "해당 product의 접근 권한이 없습니다."),
    SELECT_PRODUCT_CATEGORY_NOT_FOUND(404, "찾으시는 product의 카테고리가 일치하지 않습니다."),
    ENDDATE_IS_BEFORE_THAN_NOW(400, "마감일은 현재 시간보다 이전일 수 없습니다."),
    STARTDATE_IS_AFTER_THAN_ENDDATE(400, "시작일은 마감일보다 이후일 수 없습니다."),
    PRODUCT_REWARD_IS_NOT_NULL(400, "테스트에 보상은 반드시 들어가야 합니다."),
    TITLE_NULL_EXCEPTION(400, "제목은 빈 값이 들어갈 수 없습니다."),
    CONTENT_NULL_EXCEPTION(400, "내용은 빈 값이 들어갈 수 없습니다."),
    STARTDATE_NULL_EXCEPTION(400, "시작일에는 빈 값이 들어갈 수 없습니다."),
    CLOSEDATE_NULL_EXCEPTION(400, "마감일에는 빈 값이 들어갈 수 없습니다."),

    /* ProductLike */

    /* Reward */
    SELECT_USER_REWARD_INVALID_AUTHORIZATION(403, "본인만 수정할 수 있습니다."),
    SELECT_REWARD_NOT_FOUND(404, "찾으시는 상품을 찾을 수 없습니다."),

    /* Survey(끝) */
    SELECT_SURVEY_NOT_FOUND(404, "찾으시는 설문지가 존재하지 않습니다."),
    SELECT_SURVEY_INVALID_AUTHORIZATION(403, "설문지에 대한 접근권한이 없습니다."),

    /* Util(S3Uploader) */
    SELECT_IMAGE_NOT_FOUND(404, "해당 이미지를 찾을 수 없습니다."),
    CREATE_IMAGE_FAIL(404, "해당 이미지 업로드에 실패했습니다."),
    SELECT_IMAGE_NOT_READABLE(422, "해당 이미지를 불러올 수 없습니다."),
    SELECT_IMAGE_NOT_RESIZABLE(500, "파일을 줄이는데 실패했습니다.");

    private final int status;

    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
