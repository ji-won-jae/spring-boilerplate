package com.project.api.model.response.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Data
@Builder
public class JwtTokenResDto {
    @Schema(description = "accessToken", example = "Token Value")
    private String accessToken;
    @Schema(description = "refreshToken", example = "Refresh Token Value")
    private String refreshToken;

    public static JwtTokenResDto of(String accessToken, String refreshToken) {
        return JwtTokenResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
