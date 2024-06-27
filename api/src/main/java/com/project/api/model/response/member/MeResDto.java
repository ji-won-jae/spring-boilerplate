package com.project.api.model.response.member;

import com.project.core.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeResDto {
    @Schema(description = "사용자 PK", example = "1")
    private Long memberId;
    @Schema(description = "닉네임", example = "wonjae")
    private String nickname;
    @Schema(description = "이메일", example = "wonjae@gmail.com")
    private String email;

    public static MeResDto from(Member member) {
        return MeResDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }
}
