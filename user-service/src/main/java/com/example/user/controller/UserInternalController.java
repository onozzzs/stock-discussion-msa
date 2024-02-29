package com.example.user.controller;

import com.example.user.dto.UserDTO;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/internal/user")
public class UserInternalController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/getUserByUsername")
    UserDTO getUserByUsername(@RequestParam(value = "username") String username) {
        User user = userRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO(user);
        return userDTO;
    }

    @GetMapping("/getUserByUserId")
    UserDTO getUserByUserId(@RequestParam(value = "userId") String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        UserDTO userDTO = new UserDTO(user);
        return userDTO;
    }
}
