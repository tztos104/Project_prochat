package com.example.yj_userservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "패스워드가 틀립니다."),
    DUPLICATED_MEMBER_ID(HttpStatus.CONFLICT, "이미 있는 아이디입니다."), //맴버아이뒤 틀림
    ALREADY_LIKED_POST(HttpStatus.CONFLICT, "user already like the post"),
    ALREADY_LIKED_COMMENT(HttpStatus.CONFLICT, "user already like the comment"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs"),
    NOTIFICATION_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Connect to notification occurs error"),
    DUPLICATED_FOLLOW(HttpStatus.CONFLICT, "Already followed"),
    INVALID_FOLLOW(HttpStatus.UNAUTHORIZED, "자신을 팔로우 할 수 없습니다."),
    INVALID_EMAIL_OR_CERTIFICATION_NUMBER(HttpStatus.UNAUTHORIZED, "잘못된 이메일 또는 인증번호입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "Email not found"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment not found"),
    ;

    private final HttpStatus status;
    private final String message;
}
