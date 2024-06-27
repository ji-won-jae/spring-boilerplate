package com.project.api.model.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordReqDto {

    @Schema(description = "이전 비밀번호", example = "password123!")
    @NotBlank(message = "이전 비밀번호는 필수로 입력되어야 합니다.")
    private String oldPassword;

    @Schema(description = "새로운 비밀번호", example = "password123!")
    @NotBlank(message = "새로운 비밀번호는 필수로 입력되어야 합니다.")
    private String newPassword;
}
