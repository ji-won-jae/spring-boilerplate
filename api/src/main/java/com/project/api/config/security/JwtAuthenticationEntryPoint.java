package com.project.api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.exception.ErrorResponse;
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

        ErrorResponse of = ErrorResponse.of(403, exception);

        response.getWriter().write(objectMapper.writeValueAsString(of));
        log.error("exception {} {} {}", exception, request.getRequestURI(), request.getRemoteHost());
    }
}
