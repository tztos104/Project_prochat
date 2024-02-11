package com.example.yj_userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileRequest {
    private String name;
    private String phone;
    private String address;

    private String profile;
    private String image;

    public UserProfileRequest() {
    }
}
