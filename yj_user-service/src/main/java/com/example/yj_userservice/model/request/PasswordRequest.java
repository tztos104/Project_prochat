package com.example.yj_userservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordRequest {

    private String memberPw;
    private String newPassword;
    private String token;

    public PasswordRequest() {
    }
}
