package com.example.yj_userservice.controller;


import com.example.yj_userservice.config.ClassUtils;
import com.example.yj_userservice.model.Users;

import com.example.yj_userservice.model.request.*;
import com.example.yj_userservice.model.response.*;
import com.example.yj_userservice.repository.UserRepository;
import com.example.yj_userservice.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "UsersController", description = "로그인 관련 Api")
@RequestMapping("/api/Users")
@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final UserRepository userRepository;
    @GetMapping("/api")
    public String status() {
        return "susses-user-service";

    }
    @Operation(summary = "회원 가입 API")
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        return Response.success(UserJoinResponse.fromMember(
                userService.join( request.getEmail(), request.getMemberPw())));
    }


    @Operation(summary = "로그아웃 API")
    @PostMapping("/logout")
    public Response<UserLoginResponse> logout(@RequestBody UserLogoutRequest request, Authentication authentication) {
        Users user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        userService.logout(request.getToken(), user.getEmail());

        return Response.success(new UserLoginResponse(request.getToken()));
    }
    @Operation(summary = "모든 기기 로그아웃 API")
    @PostMapping("/logout/all")
    public Response<UserLoginResponse> logoutAll(@RequestBody UserLogoutRequest request,Authentication authentication) {
        Users user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        userService.logout(request.getToken(), user.getEmail());

        return Response.success(new UserLoginResponse(request.getToken()));
    }

    @Operation(summary = "프로필 업데이트 API")
    @PutMapping("/profile_update")
    public Response<UserProfileResponse> updateProfile(@RequestBody UserProfileRequest request, Authentication authentication) {
        Users user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        return Response.success(UserProfileResponse.fromMember(
                userService.updateProfile(
                        user.getEmail(), request.getName(), request.getPhone(),
                        request.getAddress(), request.getProfile(), request.getImage())));


    }


    //비밀번호업데이트
    @Operation(summary = "비밀번호 업데이트 API")
    @PutMapping("/password_update")
    public Response<Void> updatePassword(@RequestBody PasswordRequest request, Authentication authentication) {
        Users user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        userService.updatePassword(user.getEmail(), request.getMemberPw(), request.getNewPassword());
        userService.logout(request.getToken(), user.getEmail());
        return Response.success();
    }
    @Operation(summary = "마이 페이지 API")
    @GetMapping("/me")
    public Response<UserJoinResponse> me(Authentication authentication) {

        return Response.success(UserJoinResponse.fromMember(userService.loadUserByUsername(authentication.getName())));
    }
    @Operation(summary = "내 아이디 찾기 API")
    @GetMapping("/find-by-email")
    public UserResponse findByEmail(@RequestBody String email) {
        // 여기서 userService를 통해 이메일을 기반으로 유저를 찾아서 UserResponse로 변환하여 반환
        Users user = userService.loadUserByUsername(email);
        return UserResponse.fromUser(user);
    }




}
