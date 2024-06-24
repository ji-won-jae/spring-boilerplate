package com.project.api.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.model.common.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException {
        log.debug("filterChain");
        String exception = (String) request.getAttribute("UnauthorizedException");

        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(403, exception)));
        log.error("exception {} {} {}", exception, request.getRequestURI(), request.getRemoteHost());
    }
}
