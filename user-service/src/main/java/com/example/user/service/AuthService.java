package com.example.user.service;

import com.example.user.dto.UserResponseDTO;
import com.example.user.dto.UserRequestDTO;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserResponseDTO signup(final UserRequestDTO userRequestDTO) {
        if (exist(userRequestDTO)) throw new RuntimeException("user already exists");
        User user = User.builder()
                .mail(userRequestDTO.getMail())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .username(userRequestDTO.getUsername())
                .build();

        userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getMail(), user.getPassword(), user.getUsername(), user.isStatus());
        return userResponseDTO;
    }

    private boolean exist(UserRequestDTO userRequestDTO) {
        return userRepository.existsByUsername(userRequestDTO.getUsername())
                || userRepository.existsByMail(userRequestDTO.getMail());
    }
}
