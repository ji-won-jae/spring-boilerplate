package com.project.api.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
    //// Success
    SUCCESS("0000", "정상 처리되었습니다."),
    SUCCESS_VALID_MOBILE("0000", "사용 가능한 전화번호입니다."),
    SUCCESS_VALID_ID("0000", "사용 가능한 아이디입니다."),
    
    //// Failed
    FAILED_USER_DUP("1000", "이미 존재하는 아이디입니다."),

    //// Exception
    EXCEPTION("1000", "잘못된 요청입니다."),


    //Invalid
    EXCEPTION_ENTERED_INVALID_CARD("1001", "카드 등록에 실패하였습니다."),
    EXCEPTION_ENTERED_INVALID_PAYMENT("1001", "결제에 실패했습니다. 카드 정보 및 잔액을 확인해주세요."),


    //Already Exist
    EXCEPTION_ALREADY_EXIST_USER_ID("1002", "입력하신 아이디가 이미 존재합니다."),
    EXCEPTION_ALREADY_EXIST("1002", "입력하신 정보가 이미 존재합니다."),


    //Not Matched
    EXCEPTION_NOT_MATCHED("1003", "입력하신 정보가 일치하지 않습니다."),
    EXCEPTION_NOT_MATCHED_USER_EMAIL_PASSWORD("1003", "입력하신 아이디, 비밀번호가 일치하지 않습니다."),
    EXCEPTION_NOT_MATCHED_CODE("1003", "인증번호가 올바르지 않습니다."),
    EXCEPTION_NOT_MATCHED_CODE_EXPIRED("1003", "만료된 인증번호입니다."),
    EXCEPTION_NOT_MATCHED_PASSWORD("1003", "입력하신 비밀번호가 일치하지 않습니다."),

    //// BadRequest Exception
    EXCEPTION_BAD_REQUEST("4000", "잘못된 요청입니다."),
    EXCEPTION_BAD_REQUEST_NOT_ENTERED_USER_ID("4001", "아이디가 입력되지 않았습니다."),

    //// Unauthorized Exception
    EXCEPTION_UNAUTHORIZED("4010", "인증되지 않은 계정입니다."),


    //// Forbidden Exception
    EXCEPTION_FORBIDDEN("4030", "접근권한이 없습니다."),

    //// NotFound Exception
    EXCEPTION_NOT_FOUND("4040", "요청하신 정보를 찾을 수 없습니다."),


    EXCEPTION_NOT_FOUND_SNS_USER("40411", "요청하신 계정을 찾을 수 없습니다."),
    EXCEPTION_NOT_FOUND_USER("4041", "요청하신 계정을 찾을 수 없습니다."),

    ;

    private final String code;
    private final String message;

    ResponseCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
