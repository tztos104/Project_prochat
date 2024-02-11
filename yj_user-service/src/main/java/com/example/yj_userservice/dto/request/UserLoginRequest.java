package com.example.yj_userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {

    private String email;
    private String memberPw;

    public UserLoginRequest() {
    }
}
