package com.example.yj_userservice.service;

import com.example.yj_userservice.dto.Users;
import com.example.yj_userservice.dto.entity.UsersEntity;
import com.example.yj_userservice.exception.ErrorCode;
import com.example.yj_userservice.exception.ProchatException;
import com.example.yj_userservice.repository.CacheRepository;
import com.example.yj_userservice.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final CacheRepository redisRepository;
    private final CircuitBreakerFactory circuitBreakerFactory;


    public Users loadUserByUsername(String email) throws UsernameNotFoundException {
        return redisRepository.getUser(email).orElseGet(
                () -> userRepository.findByEmail(email).map(Users::fromEntity).orElseThrow(
                        () -> new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", email))
                ));
    }
    //회원가입 로직
    @Transactional
    public Users join(String Email, String memberPw){

        userRepository.findByEmail(Email).ifPresent(it ->{
            throw new ProchatException(ErrorCode.DUPLICATED_MEMBER_ID, String.format("%s 는 이미 있는 이메일입니다", Email));
        });
       UsersEntity savedUser = userRepository.save(UsersEntity.of(Email, encoder.encode(memberPw)));
        return Users.fromEntity(savedUser);

    }


    //이메일로 유저아이디 찾기
    @Transactional
    public Users getUserDetailsByEmail(String email) {
        UsersEntity usersEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new ProchatException(ErrorCode.EMAIL_NOT_FOUND));


        return Users.fromEntity(usersEntity);
    }

    //로그아웃 로직
    @Transactional
    public void logout(String token, String memberId){
        Users savedUser = loadUserByUsername(memberId);
        if (redisRepository.isBlackListUserOne(token, memberId)) {

            throw new RuntimeException("Logout denied for blacklisted user");
        }
        redisRepository.setBlackListUser(token,savedUser);
    }




    //프로필 업데이트
    @Transactional
    public Users updateProfile(String email, String name, String phone, String address,
                                 String profile, String image) {
        UsersEntity entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ProchatException(ErrorCode.USER_NOT_FOUND, "멤버를 찾을 수 없습니다"));


        entity.setName(name);
        entity.setPhone(phone);
        entity.setAddress(address);
        entity.setProfile(profile);
        entity.setImage(image);

        // 업데이트된 멤버 정보로 변환하여 반환
        return Users.fromEntity(userRepository.saveAndFlush(entity));
    }

    //비밀번호 업데이트

    @Transactional
    public Users updatePassword(String email, String memberPw, String newPassword) {
        UsersEntity member = userRepository.findByEmail(email)
                .orElseThrow(() -> new ProchatException(ErrorCode.USER_NOT_FOUND, "멤버를 찾을 수 없습니다"));

        // 현재 비밀번호 확인
        if (!encoder.matches(memberPw, member.getMemberPw())) {
            System.out.println(memberPw+":"+member.getMemberPw());
            throw new ProchatException(ErrorCode.INVALID_PASSWORD, "현재 비밀번호가 일치하지 않습니다");
        }
        // 새로운 비밀번호로 업데이트
        member.setMemberPw(encoder.encode(newPassword));
        // 업데이트된 멤버 정보로 변환하여 반환
        return Users.fromEntity(userRepository.save(member));
    }

    //CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");




}
