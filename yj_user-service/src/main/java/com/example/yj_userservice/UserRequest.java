package com.example.yj_userservice;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotNull(message = "이메일이 작성되지 않았습니다.")
    @Size(min = 2, message = "이메일은 두글자이상보다 길어야합니다.")
    @Email
    private String email;

    @NotNull(message = "이름이 작성되지 않았습니다.")
    @Size(min = 2, message = "2자 이상이어야 합니다..")
    private String name;

    @NotNull(message = "패스워드가 작성되지 않았습니다.")
    @Size(min = 8, message = "8자 이상이어야 합니다.")
    private String pwd;

}
