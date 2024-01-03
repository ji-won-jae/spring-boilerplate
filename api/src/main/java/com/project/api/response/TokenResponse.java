package com.project.api.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenResponse<T> {

    Status status;
    T data;

    public static <U> TokenResponse<U> success(U data) {
        return TokenResponse.<U>builder()
                .data(data)
                .status(Status.builder()
                        .success(true)
                        .code(200)
                        .message("success").build())
                .build();
    }

    public static TokenResponse<Object> fail(int code, String message) {
        return TokenResponse.builder()
                .data(null)
                .status(Status.builder()
                        .success(false)
                        .code(code)
                        .message(message).build())
                .build();
    }

}
