package com.example.yj_userservice.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String email;
    private String name;


}
