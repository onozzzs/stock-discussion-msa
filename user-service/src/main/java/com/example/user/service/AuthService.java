package com.example.user.service;

import com.example.user.dto.UserDTO;
import com.example.user.dto.UserRequestDTO;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.user.security.TokenProvider;
import com.example.user.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserDTO signup(final UserRequestDTO userRequestDTO) {
        if (exist(userRequestDTO)) throw new RuntimeException("user already exists");
        User user = User.builder()
                .mail(userRequestDTO.getMail())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .username(userRequestDTO.getUsername())
                .build();

        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user.getId(), user.getMail(), user.getPassword(), user.getUsername(), user.isStatus());
        return userDTO;
    }

    private boolean exist(UserRequestDTO userRequestDTO) {
        return userRepository.existsByUsername(userRequestDTO.getUsername())
                || userRepository.existsByMail(userRequestDTO.getMail());
    }
}
