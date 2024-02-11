package com.example.yj_userservice.config.filter;

import com.example.yj_userservice.dto.Users;
import com.example.yj_userservice.dto.entity.UsersEntity;
import com.example.yj_userservice.dto.request.UserLoginRequest;
import com.example.yj_userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class AuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    @Value("${jwt.secret-key}")
    private  String key;
    @Value("${jwt.expired-time-ms}")
    private  String expiredtime;
    private UserService userService;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            // HTTP 요청에서 사용자 로그인 정보를 읽어와 UserLoginRequest 객체로 변환
            UserLoginRequest creds = new ObjectMapper().readValue(request.getInputStream(),UserLoginRequest.class);
            // UsernamePasswordAuthenticationToken을 사용하여 AuthenticationManager를 통해 사용자를 인증
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getMemberPw(),
                            new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // 인증된 사용자 주체에서 사용자 이름을 추출
        String userName = ((User) authResult.getPrincipal()).getUsername();
        // userService에서 이메일로 사용자 세부 정보를 가져옵니다.
        Users userDetailsByEmail = userService.getUserDetailsByEmail(userName);
        // 시크릿 키를 바이트로 인코딩
        byte[] secretKeyBytes = Base64.getEncoder().encode(key.getBytes());

        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Instant now = Instant.now();
        // JWT 토큰을 생성
        String token = Jwts.builder()
                .setSubject(userDetailsByEmail.getEmail())
                .setExpiration(Date.from(now.plusMillis(Long.parseLong(expiredtime))))
                .setIssuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();

        // 생성된 토큰과 사용자 식별자를 응답 헤더에 추가
        response.addHeader("token", token);
        response.addHeader("userId", userDetailsByEmail.getEmail());
    }
}
