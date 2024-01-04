package com.project.api.web.account;

import com.project.api.model.request.account.LoginReqBody;
import com.project.api.model.request.account.RefreshTokenReqBody;
import com.project.api.model.request.account.SignUpReqBody;
import com.project.api.model.response.JwtTokenResBody;
import com.project.api.response.ResponseCode;
import com.project.api.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게정 관련 api")
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "회원 가입")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<JwtTokenResBody> signUp(@Valid @RequestBody SignUpReqBody reqBody) {

        return ResponseEntity.ok(accountService.signUp(reqBody));
    }

    @Operation(summary = "로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<JwtTokenResBody> login(@RequestBody LoginReqBody reqBody) {

        return ResponseEntity.ok(accountService.login(reqBody));
    }

    @Operation(summary = "리프레시 토큰 확인 발급")
    @PostMapping(value = "/refresh-token")
    public ResponseEntity<JwtTokenResBody> checkRefreshToken(@RequestBody RefreshTokenReqBody reqBody) throws BadRequestException {

        return ResponseEntity.ok(accountService.checkRefreshToken(reqBody));
    }
}
