package com.example.user.controller.auth;

import com.example.user.dto.UserResponseDTO;
import com.example.user.dto.UserRequestDTO;
import com.example.user.security.TokenProvider;
import com.example.user.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new RuntimeException("Invalid user value");
        }
        UserResponseDTO responseDTO = authService.signup(requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

}
