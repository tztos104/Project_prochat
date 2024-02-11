package com.example.yj_userservice.repository;


import com.example.yj_userservice.dto.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheRepository {
    private final RedisTemplate<String, Users> RedisTemplate;
    private final RedisTemplate<String, String> blacklistRedisTemplate;

    private final static Duration USER_CACHE_TTL = Duration.ofDays(3);
    private final static Duration BLACK_USER_CACHE_TTL = Duration.ofMinutes(10);
    private static final String BLACKLIST_KEY_PREFIX = "jwt:blacklist:";

    public void setUser(Users User) {
        String key = getKey(User.getEmail());
        log.info("Set User to Redis {}({})", key, User);
        RedisTemplate.opsForValue().set(key, User, USER_CACHE_TTL);
    }

    public Optional<Users> getUser(String userName) {
        Users data = RedisTemplate.opsForValue().get(getKey(userName));
        log.info("Get User from Redis {}", data);
        return Optional.ofNullable(data);
    }

    private String getKey(String userName) {
        return "UID:" + userName;
    }


    public void setBlackListUser(String token, Users member) {
        String blacklistKey = getBlacklistKey(token,member.getEmail());
        log.info("Blacklisting User: {}({})", blacklistKey, member);
        blacklistRedisTemplate.opsForValue().set(blacklistKey, token, BLACK_USER_CACHE_TTL);
    }

    public Optional<Users> getBlackListUser(String userName) {
        Users data = RedisTemplate.opsForValue().get(getKey(userName));
        log.info("Get Blacklisting User from Redis {}", data);
        return Optional.ofNullable(data);
    }

    public Boolean isBlackListUserOne(String token, String userName) {
        String blacklistKey = getBlacklistKey(token,userName);
        System.out.println(blacklistRedisTemplate.opsForValue().get(blacklistKey).matches(token));
        return blacklistRedisTemplate.opsForValue().get(blacklistKey).matches(token);
    }


    public Boolean isBlackListUserAll(String token,String userName) {
        System.out.println("여기오나");
        String blacklistKey = getBlacklistKey(token,userName);
        return blacklistRedisTemplate.hasKey(blacklistKey);
    }
    private String getBlacklistKey( String token,String userName) {
        return BLACKLIST_KEY_PREFIX + userName +token;
    }
}
