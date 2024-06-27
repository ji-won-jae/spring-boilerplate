package com.project.api.controller.member;

import com.project.api.model.common.ResponseDto;
import com.project.api.model.request.member.UpdateMyProfileResDto;
import com.project.api.model.response.member.MeResDto;
import com.project.api.model.response.member.MemberProfileResDto;
import com.project.api.principal.Account;
import com.project.api.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "사용자 관련 API")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "내 정보", description = "내 정보를 불러옵니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<MeResDto>> getMyProfile(@AuthenticationPrincipal Account account) {
        var response = memberService.getMyProfile(account);

        return ResponseDto.ok(response);
    }

    @Operation(summary = "내 정보 수정", description = "내 정보 수정합니다.")
    @PutMapping
    public ResponseEntity<Void> updateMyProfile(@AuthenticationPrincipal Account account, @RequestBody UpdateMyProfileResDto resDto) {
        memberService.updateMyProfile(account, resDto);

        return ResponseDto.noContent();
    }

    @Operation(summary = "사용자 상세 조회", description = "사용자 정보를 조회합니다.")
    @GetMapping(value = "/{memberId}")
    public ResponseEntity<ResponseDto<MemberProfileResDto>> getMemberProfile(@AuthenticationPrincipal Account account, @PathVariable Long memberId) {
        var response = memberService.getMemberProfile(account, memberId);

        return ResponseDto.ok(response);
    }


}
