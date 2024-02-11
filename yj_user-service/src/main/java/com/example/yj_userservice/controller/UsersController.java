package com.example.yj_userservice.controller;


import com.example.yj_userservice.config.ClassUtils;
import com.example.yj_userservice.dto.Users;
import com.example.yj_userservice.dto.request.*;
import com.example.yj_userservice.dto.response.Response;
import com.example.yj_userservice.dto.response.UserJoinResponse;
import com.example.yj_userservice.dto.response.UserLoginResponse;
import com.example.yj_userservice.dto.response.UserProfileResponse;
import com.example.yj_userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/health_check")
    public String status() {
        return "susses-user-service";

    }

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        return Response.success(UserJoinResponse.fromMember(
                userService.join( request.getEmail(), request.getMemberPw())));
    }

    @PostMapping("/logout")
    public Response<UserLoginResponse> logout(@RequestBody UserLogoutRequest request, Authentication authentication) {
        Users user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        userService.logout(request.getToken(), user.getEmail());

        return Response.success(new UserLoginResponse(request.getToken()));
    }
    @PostMapping("/logout/all")
    public Response<UserLoginResponse> logoutAll(@RequestBody UserLogoutRequest request,Authentication authentication) {
        Users user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        userService.logout(request.getToken(), user.getEmail());

        return Response.success(new UserLoginResponse(request.getToken()));
    }


    @PutMapping("/profile_update")
    public Response<UserProfileResponse> updateProfile(@RequestBody UserProfileRequest request, Authentication authentication) {
        Users user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        return Response.success(UserProfileResponse.fromMember(
                userService.updateProfile(
                        user.getEmail(), request.getName(), request.getPhone(),
                        request.getAddress(), request.getProfile(), request.getImage())));


    }


    //비밀번호업데이트
    @PutMapping("/password_update")
    public Response<Void> updatePassword(@RequestBody PasswordRequest request, Authentication authentication) {
        Users user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        userService.updatePassword(user.getEmail(), request.getMemberPw(), request.getNewPassword());
        userService.logout(request.getToken(), user.getEmail());
        return Response.success();
    }

    @GetMapping("/me")
    public Response<UserJoinResponse> me(Authentication authentication) {

        return Response.success(UserJoinResponse.fromMember(userService.loadUserByUsername(authentication.getName())));
    }



}
