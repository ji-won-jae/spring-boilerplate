package com.project.api.controller.member;

import com.project.api.principal.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "사용자 관련 API")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {


    @Operation(summary = "내 정보", description = "내 정보를 불러옵니다.")
    @GetMapping
    public ResponseEntity<Object> getMe(@AuthenticationPrincipal Account account) {

        return ResponseEntity.ok(account);
    }
}
