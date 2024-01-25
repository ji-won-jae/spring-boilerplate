package com.project.api.service.account;

import com.project.api.model.request.account.LoginReqBody;
import com.project.api.model.request.account.RefreshTokenReqBody;
import com.project.api.model.request.account.SignUpReqBody;
import com.project.api.model.response.JwtTokenResBody;
import org.apache.coyote.BadRequestException;

public interface AccountService {
    JwtTokenResBody signUp(SignUpReqBody reqBody);

    JwtTokenResBody login(LoginReqBody reqBody);

    JwtTokenResBody checkRefreshToken(RefreshTokenReqBody reqBody);
}
