package com.example.yj_userservice.model.response;


import com.example.yj_userservice.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponse {
    private String name;
    private String phone;
    private String address;
    private String profile;
    private String image;

    public static UserProfileResponse fromMember(Users user) {

        return new UserProfileResponse(
                user.getName(),
                user.getPhone(),
                user.getAddress(),
                user.getProfile(),
                user.getImage()
        );
    }

}
