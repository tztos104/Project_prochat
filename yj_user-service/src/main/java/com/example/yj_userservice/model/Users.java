package com.example.yj_userservice.model;


import com.example.yj_userservice.model.entity.UsersEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.sql.Timestamp;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users implements UserDetails {
   private Long id;

   private String memberPw;
   private String email;
   private MemberRole role;

   private String name;
   private String phone;
   private String address;
   private String profile;
   private String image;

   private Timestamp regDate;
   private Timestamp updateDate;
   private Timestamp removeDate;




   public static Users fromEntity(UsersEntity entity){
      return new Users(
              entity.getId(),

              entity.getMemberPw(),
              entity.getEmail(),
              entity.getRole(),
              entity.getName(),
              entity.getPhone(),
              entity.getAddress(),
              entity.getProfile(),
              entity.getImage(),
              entity.getRegDate(),
              entity.getUpdateDate(),
              entity.getRemoveDate()
      );
   }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return memberPw;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return removeDate == null;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return removeDate == null;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return removeDate == null;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return removeDate == null;
    }
}