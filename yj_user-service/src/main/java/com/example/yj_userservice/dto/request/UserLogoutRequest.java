package com.example.yj_userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLogoutRequest {
    private String email;
    private String token;

    public UserLogoutRequest() {
    }
}
