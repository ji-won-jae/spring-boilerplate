package com.project.api.service.account;

import com.project.api.exception.BadRequestException;
import com.project.api.exception.NotFoundException;
import com.project.api.config.security.jwt.JwtTokenProvider;
import com.project.api.model.request.account.LoginReqDto;
import com.project.api.model.request.account.RefreshTokenReqDto;
import com.project.api.model.request.account.SignUpReqDto;
import com.project.api.model.response.account.JwtTokenResDto;

import com.project.api.repository.member.MemberRepository;
import com.project.core.domain.member.Member;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtTokenResDto signUp(SignUpReqDto reqBody) {
        var member = memberRepository.save(
                Member.of(
                        reqBody.getNickname()
                        , reqBody.getEmail()
                        , passwordEncoder.encode(reqBody.getPassword())
                )
        );
        String accessToken = jwtTokenProvider.createJwt(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshJwt(accessToken);

        return JwtTokenResDto.of(accessToken, refreshToken);
    }

    @Transactional
    public JwtTokenResDto login(LoginReqDto reqBody) {
        var member = memberRepository.findByEmail(reqBody.getEmail())
                .orElseThrow(() -> new NotFoundException("등록된 사용자가 아닙니다."));

        if (!passwordEncoder.matches(reqBody.getPassword(), member.getPassword())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
        String accessToken = jwtTokenProvider.createJwt(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshJwt(accessToken);

        return JwtTokenResDto.of(accessToken, refreshToken);
    }

    @Transactional
    public JwtTokenResDto checkRefreshToken(RefreshTokenReqDto reqBody) {
        if (!jwtTokenProvider.validateToken(reqBody.getRefreshToken())) {
            throw new NotFoundException("토큰 정보가 만기 되었습니다. 재로그인 해주세요.");
        }
        Claims claims = jwtTokenProvider.getClaims(reqBody.getRefreshToken());
        String accessToken = jwtTokenProvider.createJwt(Long.parseLong(claims.getSubject()));

        return JwtTokenResDto.of(accessToken, reqBody.getRefreshToken());
    }
}
