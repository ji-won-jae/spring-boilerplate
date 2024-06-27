package com.project.api.config.security.jwt;


import com.project.api.exception.UnAuthorizedException;
import com.project.api.principal.MemberDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;

@Slf4j
@Component
public class JwtTokenProvider {
    private Key key;

    @Value("${jwt.access.expire-second}")
    private Long accessTokenExpireSecond;
    @Value("${jwt.refresh.expire-second}")
    private Long refreshTokenExpireSecond;

    private final MemberDetailService memberDetailService;

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey,
                            MemberDetailService memberDetailService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.memberDetailService = memberDetailService;
    }

    public String createJwt(Long memberId) {

        Instant issuedDt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expireDt = issuedDt.plus(accessTokenExpireSecond, ChronoUnit.SECONDS);

        return Jwts.builder()
                .setSubject(memberId.toString())
                .setIssuedAt(Date.from(issuedDt))
                .setExpiration(Date.from(expireDt))
                .signWith(key)
                .compact();
    }

    public String createRefreshJwt(String token) {
        Claims claims = getClaims(token);

        Instant issuedDt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expireDt = issuedDt.plus(refreshTokenExpireSecond, ChronoUnit.SECONDS);

        return Jwts.builder()
                .setSubject(claims.getSubject())
                .setIssuedAt(Date.from(issuedDt))
                .setExpiration(Date.from(expireDt))
                .signWith(key)
                .compact();
    }


    public boolean isExpiredToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            Date expireDt = claims.getBody().getExpiration();
            Date nowDt = new Date();

            return expireDt.before(nowDt);
        } catch (Exception e) {
            return true;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            Date expireDt = claims.getBody().getExpiration();
            Date nowDt = new Date();

            if (expireDt.after(nowDt)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new UnAuthorizedException("토큰이 유효하지 않습니다.");
        }
    }

    public Authentication getAuthentication(String jwt) {

        String memberId = getClaims(jwt).getSubject();

        if (key == null) {
            throw new UnAuthorizedException("등록되지 않은 토큰입니다.");
        }

        HashSet<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));


        return new UsernamePasswordAuthenticationToken(memberDetailService.loadUserByUsername(memberId), jwt, authorities);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }


}
