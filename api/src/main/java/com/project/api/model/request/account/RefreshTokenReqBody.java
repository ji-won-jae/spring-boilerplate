package com.project.api.model.request.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenReqBody {
    @NotNull(message = "토큰을 입력해 주세요.")
    private String refreshToken;
}
