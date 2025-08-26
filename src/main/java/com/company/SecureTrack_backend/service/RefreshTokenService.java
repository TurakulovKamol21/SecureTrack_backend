package com.company.SecureTrack_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(7);

    public void storeRefreshToken(UUID userId, String token) {
        String key = buildKey(userId);
        redisTemplate.opsForValue().set(key, token, REFRESH_TOKEN_DURATION);
    }

    public boolean isValid(UUID userId, String token) {
        String key = buildKey(userId);
        String savedToken = redisTemplate.opsForValue().get(key);
        return token.equals(savedToken);
    }

    public void deleteRefreshToken(UUID userId) {
        redisTemplate.delete(buildKey(userId));
    }

    private String buildKey(UUID userId) {
        return "refresh_token:" + userId;
    }
}
