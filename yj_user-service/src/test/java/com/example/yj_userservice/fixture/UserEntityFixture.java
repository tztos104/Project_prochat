package com.example.yj_userservice.fixture;



import com.example.yj_userservice.model.MemberRole;
import com.example.yj_userservice.model.entity.UsersEntity;

import java.sql.Timestamp;
import java.time.Instant;

public class UserEntityFixture {

    public static UsersEntity get(String userName, String password) {
        UsersEntity entity = new UsersEntity();
        entity.setId(1L);
        entity.setName(userName);
        entity.setMemberPw(password);
        entity.setRole(MemberRole.MEMBER);
        entity.setRegDate(Timestamp.from(Instant.now()));
        return entity;
    }
}
