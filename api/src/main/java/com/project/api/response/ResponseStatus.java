package com.project.api.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseStatus implements Serializable {
    private static final long serialVersionUID = 2490569837353382401L;

    @Builder.Default
    private String code = ResponseCode.SUCCESS.getCode();

    @Builder.Default
    private String message = ResponseCode.SUCCESS.getMessage();
}
