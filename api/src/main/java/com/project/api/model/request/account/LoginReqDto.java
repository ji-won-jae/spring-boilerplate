package com.project.api.model.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
public class LoginReqDto {

    @Schema(description = "이메일", example = "test@gmail.com")
    @NotBlank(message = "이메일은 필수로 입력되어야 합니다.")
    private String email;

    @Schema(description = "비밀번호", example = "password123!")
    @NotBlank(message = "비밀번호는 필수로 입력되어야 합니다.")
    private String password;

}
