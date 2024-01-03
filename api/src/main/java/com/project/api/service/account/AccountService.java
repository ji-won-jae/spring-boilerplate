package com.project.api.service.account;

import com.project.api.model.response.account.SignUpReqBody;

public interface AccountService {
    void signUp(SignUpReqBody reqBody);
}
