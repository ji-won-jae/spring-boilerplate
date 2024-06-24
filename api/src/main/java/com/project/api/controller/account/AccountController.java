package com.project.api.controller.account;

import com.project.api.model.common.ResponseDto;
import com.project.api.model.request.account.LoginReqDto;
import com.project.api.model.request.account.RefreshTokenReqDto;
import com.project.api.model.request.account.SignUpReqDto;
import com.project.api.model.response.account.JwtTokenResDto;
import com.project.api.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account", description = "계정 관련 API")
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "회원 가입", description = "회원을 생성합니다.")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<ResponseDto<JwtTokenResDto>> signUp(@Valid @RequestBody SignUpReqDto reqBody) {
        var response = accountService.signUp(reqBody);

        return ResponseDto.ok(response);
    }

    @Operation(summary = "로그인", description = "토큰을 발급받습니다.")
    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDto<JwtTokenResDto>> login(@Valid @RequestBody LoginReqDto reqBody) {
        var response = accountService.login(reqBody);

        return ResponseDto.ok(response);
    }

    @Operation(summary = "리프레시 토큰 확인 발급", description = "토큰을 재발급합니다.")
    @PostMapping(value = "/reissue-token")
    public ResponseEntity<ResponseDto<JwtTokenResDto>> checkRefreshToken(@Valid @RequestBody RefreshTokenReqDto reqBody) {
        var response = accountService.checkRefreshToken(reqBody);

        return ResponseDto.ok(response);
    }
}
