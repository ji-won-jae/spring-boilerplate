package com.project.api.web.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "회원")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {


    @Operation(summary = "회원 검색")
    @GetMapping
    public ResponseEntity<String> searchMember(HttpServletRequest request) {

        return ResponseEntity.ok("지완우");
    }
}
