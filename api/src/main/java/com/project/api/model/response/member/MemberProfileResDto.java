package com.project.api.model.response.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberProfileResDto {
    @Schema(description = "사용자 PK", example = "1")
    private Long memberId;
    @Schema(description = "닉네임", example = "wonjae")
    private String nickname;
    @Schema(description = "이메일", example = "wonjae@gmail.com")
    private String email;

    public static MemberProfileResDto of(Long memberId, String nickname, String email) {
        return MemberProfileResDto.builder()
                .memberId(memberId)
                .nickname(nickname)
                .email(email)
                .build();
    }
}
