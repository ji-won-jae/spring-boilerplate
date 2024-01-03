package com.project.api.web.account;

import com.project.api.model.response.account.SignUpReqBody;
import com.project.api.response.ResponseCode;
import com.project.api.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게정 관련 api")
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "회원 가입")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<ResponseCode> signUp(@RequestBody SignUpReqBody reqBody) {

        accountService.signUp(reqBody);

        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }



    @Operation(summary = "로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<ResponseCode> login(@RequestBody SignUpReqBody reqBody) {

        accountService.signUp(reqBody);

        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }
}
