package com.project.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayloadTooLargeException extends RuntimeException {

    public PayloadTooLargeException() {
        super("요청한 값이 너무 큽니다.");
    }

    public PayloadTooLargeException(Integer limitSize) {
        super(String.format("요청한 값이 너무 큽니다. (%sMB 이하)", limitSize));
    }

    public PayloadTooLargeException(String message) {
        super(message);
    }
}
