package com.project.api.exception;

import com.project.api.model.common.ResponseCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForbiddenException extends RuntimeException {

    ResponseCode ERROR_CODE;

    public ForbiddenException() {
        super("접근권한이 없습니다.");
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, ResponseCode responseErrorCode) {
        super(message);
        this.ERROR_CODE = responseErrorCode;
    }
}
