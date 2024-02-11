package com.example.yj_userservice.dto.entity;


import com.example.yj_userservice.dto.MemberRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Table(name = "\"users\"")
@SQLDelete(sql = "UPDATE users SET remove_date = NOW() WHERE id=?")
@Where(clause = "remove_date is NULL")
@NoArgsConstructor
@Entity
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberPw;
    private String email;
    private String name;
    private String phone;
    private String address;
    private String profile;
    private String image;

    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removeDate;


    @Enumerated(EnumType.STRING)
    private MemberRole role =MemberRole.MEMBER;

    @PrePersist
    void regDate(){
        this.regDate= Timestamp.from(Instant.now());
    }
    @PreUpdate
    void updateDate(){
        this.updateDate= Timestamp.from(Instant.now());
    }



    public static UsersEntity of( String email, String memberPw) {
        UsersEntity entity = new UsersEntity();

        entity.setEmail(email);
        entity.setMemberPw(memberPw);

        return entity;
    }

    public static UsersEntity profileof(String name, String phone, String address, String profile, String image) {
        UsersEntity entity = new UsersEntity();
        entity.setName(name);
        entity.setPhone(phone);
        entity.setAddress(address);
        entity.setProfile(profile);
        entity.setImage(image);
        return entity;
    }


}
