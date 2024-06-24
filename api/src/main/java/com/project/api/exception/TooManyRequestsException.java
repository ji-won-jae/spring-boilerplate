package com.project.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TooManyRequestsException extends RuntimeException {

    public TooManyRequestsException(){
        super("너무 많은 요청을 하였습니다.");
    }

    public TooManyRequestsException(String message){
        super(message);
    }
}
