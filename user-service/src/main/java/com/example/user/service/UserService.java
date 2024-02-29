package com.example.user.service;

import com.example.user.dto.PasswordDTO;
import com.example.user.dto.UserResponseDTO;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.user.security.TokenProvider;
import com.example.user.util.EncryptionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private EncryptionUtils encryptionUtils;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public void updatePassword(HttpServletRequest request, PasswordDTO passwordDTO) {
        String userId = getUserId(request);
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        Boolean isValid = validatePassword(passwordDTO.getOldPassword(), user.getPassword());
        if (!isValid) {
            throw new RuntimeException("Invalid password");
        }

        user.updatePassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
    }

    @Transactional
    public void updateFile(HttpServletRequest request, final String fileUrl) {
        String userId = getUserId(request);
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        user.updateProfileUrl(fileUrl);
    }

    @Transactional
    public void updateUser(HttpServletRequest request, UserResponseDTO userResponseDTO) {
        String userId = getUserId(request);
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        user.updateUsernameAndContent(userResponseDTO.getUsername(), userResponseDTO.getContent());
    }

    private Boolean validatePassword(final String oldPassword, final String encryptedPassword) {
        return encryptionUtils.validatePassword(oldPassword, encryptedPassword);
    }

    private String getUserId(HttpServletRequest request) {
        String token = tokenProvider.resolveAccessToken(request);
        return tokenProvider.validateAndGetUserId(token);
    }
}
