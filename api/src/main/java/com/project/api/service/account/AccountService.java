package com.project.api.service.account;

import com.project.api.exception.BadRequestException;
import com.project.api.exception.ConflictException;
import com.project.api.exception.NotFoundException;
import com.project.api.config.security.jwt.JwtTokenProvider;
import com.project.api.model.request.account.*;
import com.project.api.model.response.account.JwtTokenResDto;

import com.project.api.principal.Account;
import com.project.api.repository.member.MemberRepository;
import com.project.core.domain.member.Member;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtTokenResDto signUp(SignUpReqDto reqBody) {
        if (existByEmail(reqBody.getEmail())) {
            throw new ConflictException("이미 존재하는 이메일입니다.");
        }
        var member = memberRepository.save(
                Member.of(
                        reqBody.getNickname()
                        , reqBody.getEmail()
                        , passwordEncoder.encode(reqBody.getPassword())
                )
        );
        return createToken(member);
    }

    @Transactional
    public JwtTokenResDto login(LoginReqDto reqBody) {
        var member = memberRepository.findByEmail(reqBody.getEmail())
                .orElseThrow(() -> new NotFoundException("등록된 사용자가 아닙니다."));

        if (!passwordEncoder.matches(reqBody.getPassword(), member.getPassword())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
        return createToken(member);
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

    @Transactional
    public void updatePassword(Account account, UpdatePasswordReqDto reqDto) {
        var member = memberRepository.findById(account.getId()).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(reqDto.getOldPassword(), member.getPassword())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
        if (!Objects.equals(reqDto.getOldPassword(), reqDto.getNewPassword())) {
            throw new BadRequestException("이전 비밀번호와 동일하게 설정할 수 없습니다.");
        }
        member.updatePassword(passwordEncoder.encode(reqDto.getNewPassword()));
    }

    @Transactional
    public void logout(Account account, LogoutReqDto reqBody) {
        var member = memberRepository.findById(account.getId()).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        member.setRefreshToken("logout");
    }

    @Transactional(readOnly = true)
    public boolean existByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    //토큰 생성
    public JwtTokenResDto createToken(Member member) {
        String accessToken = jwtTokenProvider.createJwt(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshJwt(accessToken);
        member.updateRefreshToken(refreshToken);

        return JwtTokenResDto.of(accessToken, refreshToken);
    }
}
