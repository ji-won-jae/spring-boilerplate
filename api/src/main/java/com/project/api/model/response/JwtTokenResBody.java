package com.project.api.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenResBody {

    private String accessToken;

    private String refreshToken;

}
