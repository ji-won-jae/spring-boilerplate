package com.project.api.model.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private Integer code;

    private String message;

    @Builder
    public  ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Builder
    public ErrorResponse(ResponseCode responseCode) {
        this.code = Integer.valueOf(responseCode.getCode());
        this.message = responseCode.getMessage();
    }
}
