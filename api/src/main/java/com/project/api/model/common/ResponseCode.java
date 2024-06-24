package com.project.api.model.common;

import lombok.Getter;

@Getter
public enum ResponseCode {

    //// Success
    SUCCESS("200", "정상 처리되었습니다."),
    //// BadRequest Exception
    EXCEPTION_BAD_REQUEST("400", "잘못된 요청입니다."),
    //// Unauthorized Exception
    EXCEPTION_UNAUTHORIZED("401", "인증되지 않은 계정입니다."),  //// Forbidden Exception
    //// Forbidden Exception
    EXCEPTION_FORBIDDEN("403", "접근권한이 없습니다."),
    //// NotFound Exception
    EXCEPTION_NOT_FOUND("404", "요청하신 정보를 찾을 수 없습니다."),
    //Already Exist
    EXCEPTION_ALREADY_EXIST("409", "입력하신 정보가 이미 존재합니다."),
    ;
    private final String code;
    private final String message;

    ResponseCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
