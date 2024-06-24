package com.project.api.exception;

import com.project.api.model.common.ResponseCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 409 Conflict(충돌)
 * 다른 요청이나 서버의 구성과 충돌이 있음을 나타냅니다.
 * 충돌에 대한 정보는 응답되는 데이터의 일부로 반환됩니다. 이 코드는 사용자가 충돌을 해결하고 요구를 재전송할 수 있을 것으로 기대할 수 있는 상황에서만 사용할 수 있습니다.
 * 응답 본문은 사용자가 충돌의 원인을 인지할 수 있도록 충분한 정보를 포함해야 합니다.
 * 이상적으로는 응답 엔터티가 사용자 또는 사용자 에이전트가 문제를 해결할 수 있을 정도의 충분한 정보를 포함할 수 있을 것입니다. 그러나 가능하지 않을 수도 있으며 필수 사항은 아닙니다.
 */
@Getter
@Setter
public class ConflictException extends RuntimeException {

    private static final long serialVersionUID = -8575158703655182875L;

    ResponseCode ERROR_CODE;

    public ConflictException() {
        super("충돌");
    }

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, ResponseCode responseErrorCode) {
        super(message);
        this.ERROR_CODE = responseErrorCode;
    }
}
