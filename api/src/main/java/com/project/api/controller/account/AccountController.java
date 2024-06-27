package com.project.api.controller.account;

import com.project.api.model.common.ResponseDto;
import com.project.api.model.request.account.*;
import com.project.api.model.response.account.JwtTokenResDto;
import com.project.api.principal.Account;
import com.project.api.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account", description = "계정 관련 API")
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "회원 가입", description = "회원을 생성합니다.")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<ResponseDto<JwtTokenResDto>> signUp(@Valid @RequestBody SignUpReqDto reqBody) {
        var response = accountService.signUp(reqBody);

        return ResponseDto.ok(response);
    }

    @Operation(summary = "이메일 유효성 검사", description = "이메일의 유효성을 검사합니다.")
    @GetMapping(value = "/valid-email")
    public ResponseEntity<ResponseDto<Boolean>> validEmail(@RequestParam String email) {
        var response = accountService.existByEmail(email);

        return ResponseDto.ok(response);
    }

    @Operation(summary = "로그인", description = "Access Token 과 Refresh Token 을 생성합니다.")
    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDto<JwtTokenResDto>> login(@Valid @RequestBody LoginReqDto reqBody) {
        var response = accountService.login(reqBody);

        return ResponseDto.ok(response);
    }

    @Operation(summary = "로그아웃", description = "Refresh Token 을 제거합니다.")
    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal Account account, @Valid @RequestBody LogoutReqDto reqBody) {
        accountService.logout(account, reqBody);

        return ResponseDto.noContent();
    }


    @Operation(summary = "리프레시 토큰 확인 발급", description = "Refresh Token 으로 Access Token 을 재발급합니다.")
    @PostMapping(value = "/reissue-token")
    public ResponseEntity<ResponseDto<JwtTokenResDto>> checkRefreshToken(@Valid @RequestBody RefreshTokenReqDto reqBody) {
        var response = accountService.checkRefreshToken(reqBody);

        return ResponseDto.ok(response);
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호륿 변경합니다.")
    @PutMapping(value = "/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal Account account, @RequestBody @Valid UpdatePasswordReqDto reqDto) {
        accountService.updatePassword(account, reqDto);

        return ResponseDto.noContent();
    }
}
