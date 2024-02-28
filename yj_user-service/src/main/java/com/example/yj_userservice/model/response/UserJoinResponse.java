package com.example.yj_userservice.model.response;


import com.example.yj_userservice.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserJoinResponse {

    private Long id;
    private String email;


    public static UserJoinResponse fromMember(Users user) {
        return new UserJoinResponse(
                user.getId(),
                user.getEmail()

        );

    }

}
