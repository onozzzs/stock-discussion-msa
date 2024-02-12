package com.example.user.service;

import com.example.user.dto.TokenDTO;
import com.example.user.dto.UserRequestDTO;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.user.security.TokenProvider;
import com.example.user.util.EncryptionUtils;
import com.example.user.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final RedisUtil redisUtil;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public TokenDTO login(UserRequestDTO requestDTO) {
        String mail = requestDTO.getMail();
        String password = requestDTO.getPassword();

        User user = userRepository.findByMail(mail);
        if (user == null) {
            throw new RuntimeException("Incorrect email");
        }

        if (!EncryptionUtils.validatePassword(password, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        String accessToken = tokenProvider.create(mail, user.getId());
        String refreshToken = tokenProvider.create(mail, user.getId());

        Long expiration = tokenProvider.getExpiration(refreshToken);
        redisUtil.setValues("RT:" + user.getId(), refreshToken, expiration, TimeUnit.MILLISECONDS);

        return new TokenDTO(accessToken, refreshToken, expiration);
    }
}
