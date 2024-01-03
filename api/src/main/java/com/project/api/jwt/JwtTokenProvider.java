package com.project.api.jwt;


import com.project.api.principal.MemberDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;

@Slf4j
@Component
public class JwtTokenProvider {
    private Key key;

    //    long accessTokenExpired = 1000 * 60 * 30;
    long accessTokenExpired = 1000 * 30;

    long refreshTokenExpired = 1000L * 60 * 60 * 24 * 30;
    private final MemberDetailService memberDetailService;

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey,
                            MemberDetailService memberDetailService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.memberDetailService = memberDetailService;
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

    public boolean validateToken(String token) throws BadRequestException {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            Date expireDt = claims.getBody().getExpiration();
            Date nowDt = new Date();

            if (!expireDt.before(nowDt)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new BadRequestException("토큰이 유효하지 않습니다.");
        }
    }

    public Authentication getAuthentication(String jwt) throws BadRequestException {

        String key = getClaimsSub(jwt);

        if (key == null) {
            throw new BadRequestException("등록되지 않은 토큰입니다.");
        }

        HashSet<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));


        return new UsernamePasswordAuthenticationToken(memberDetailService.loadUserByUsername(key), jwt, authorities);
    }

    private String getClaimsSub(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }



}
