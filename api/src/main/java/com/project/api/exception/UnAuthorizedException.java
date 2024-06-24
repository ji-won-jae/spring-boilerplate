package com.project.api.exception;

import com.project.api.model.common.ResponseCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnAuthorizedException extends RuntimeException {

    ResponseCode ERROR_CODE;

    public UnAuthorizedException() {
        super("인증되지 않은 계정입니다.");
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String message, ResponseCode responseErrorCode) {
        super(message);
        this.ERROR_CODE = responseErrorCode;
    }
}
