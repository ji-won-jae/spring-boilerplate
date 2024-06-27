package com.project.api.model.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogoutReqDto {

    @Schema(description = "refresh token", example = "token")
    @NotBlank(message = "리프레시 토큰을 입력해주세요.")
    private String refreshToken;
}
