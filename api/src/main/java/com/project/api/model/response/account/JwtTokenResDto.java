package com.project.api.model.response.account;

import lombok.*;


@Data
@Builder
public class JwtTokenResDto {

    private String accessToken;

    private String refreshToken;


    public static JwtTokenResDto of(String accessToken, String refreshToken) {
        return JwtTokenResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
