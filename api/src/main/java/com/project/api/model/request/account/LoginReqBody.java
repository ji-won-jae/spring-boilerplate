package com.project.api.model.request.account;

import lombok.Data;

@Data
public class LoginReqBody {

    private String email;

    private String password;
}
