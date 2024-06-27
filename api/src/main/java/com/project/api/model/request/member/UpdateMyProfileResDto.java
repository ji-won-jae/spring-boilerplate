package com.project.api.model.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateMyProfileResDto {

    @Schema(description = "소개글", example = "hi")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Schema(description = "닉네임", example = "wonjae")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
}
