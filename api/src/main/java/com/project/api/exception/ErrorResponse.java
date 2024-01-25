package com.project.api.exception;

import lombok.Data;

@Data
public class ErrorResponse {

    private Integer code;

    private String message;


    public static ErrorResponse of(Integer code, String message) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        return errorResponse;
    }
}
