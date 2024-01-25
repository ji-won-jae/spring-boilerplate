package com.project.api.service.account;

import com.project.api.exception.NotFoundException;
import com.project.api.jwt.JwtTokenProvider;
import com.project.api.model.request.account.LoginReqBody;
import com.project.api.model.request.account.RefreshTokenReqBody;
import com.project.api.model.request.account.SignUpReqBody;
import com.project.api.model.response.JwtTokenResBody;

import com.project.api.repository.member.MemberRepository;
import com.project.core.domain.member.Member;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public JwtTokenResBody signUp(SignUpReqBody reqBody) {

        Member member = Member.of(reqBody.getNickname(), reqBody.getEmail(), passwordEncoder.encode(reqBody.getPassword()));
        Member newMember = memberRepository.save(member);

        String accessToken = jwtTokenProvider.createJwt(newMember.getId());

        String refreshToken = jwtTokenProvider.createRefreshJwt(accessToken);

        return JwtTokenResBody.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public JwtTokenResBody login(LoginReqBody reqBody) {
        Member member = memberRepository.findByEmail(reqBody.getEmail())
                .orElseThrow(() -> new NotFoundException("등록된 사용자가 아닙니다."));

        if (!passwordEncoder.matches(reqBody.getPassword(), member.getPassword())) {
            throw new NotFoundException("비밀번호가 일치하지 않습니다.");
        }
        String accessToken = jwtTokenProvider.createJwt(member.getId());

        String refreshToken = jwtTokenProvider.createRefreshJwt(accessToken);

        return JwtTokenResBody.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public JwtTokenResBody checkRefreshToken(RefreshTokenReqBody reqBody){

        if (jwtTokenProvider.validateToken(reqBody.getRefreshToken())) {
            Claims claims = jwtTokenProvider.getClaims(reqBody.getRefreshToken());
            String accessToken = jwtTokenProvider.createJwt(Long.parseLong(claims.getSubject()));
            return JwtTokenResBody.builder()
                    .accessToken(accessToken)
                    .refreshToken(reqBody.getRefreshToken())
                    .build();
        }else{
            throw new NotFoundException("토큰 정보가 만기 되었습니다. 재로그인 해주세요.");
        }
    }
}
