package com.example.user.service;

import com.example.user.security.TokenProvider;
import com.example.user.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class LogoutService {
    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    RedisUtil redisUtil;

    public void logout(final HttpServletRequest request) {
        String token = tokenProvider.resolveAccessToken(request);
        if (redisUtil.keyExists(token)) {
            throw new RuntimeException("Already logout");
        }

        String userId = tokenProvider.validateAndGetUserId(token);
        String redisKey = "RT:" + userId;
        if (redisUtil.getValue(redisKey) != null) {
            redisUtil.deleteKey(redisKey);
        }

        Long expiration = tokenProvider.getExpiration(token);
        redisUtil.setValues(token, "", expiration, TimeUnit.MILLISECONDS);
    }
}
