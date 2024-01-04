package com.project.api.model.request.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpReqBody {

    @NotNull(message = "닉네임을 입력해 주세요.")
    private String nickname;
    @NotNull(message = "이메일을 입력해 주세요.")
    private String email;
    @NotNull(message = "패스워드를 입력해 주세요.")
    private String password;
}
