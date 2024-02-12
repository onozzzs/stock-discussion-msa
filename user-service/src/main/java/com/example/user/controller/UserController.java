package com.example.user.controller;

import com.example.user.dto.PasswordDTO;
import com.example.user.dto.UserDTO;
import com.example.user.service.S3UploadService;
import com.example.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private S3UploadService uploadService;

    @Autowired
    private UserService userService;

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDTO passwordDTO, HttpServletRequest request) {
        if (passwordDTO == null) {
            throw new RuntimeException("Empty password");
        }
        userService.updatePassword(request, passwordDTO);
        return ResponseEntity.ok().body("Password change completed");
    }

    @PostMapping("/updateFile")
    public ResponseEntity<?> updateFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String fileUrl = uploadService.saveFile(file);
        userService.updateFile(request, fileUrl);
        return ResponseEntity.ok().body("File upload completed");
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        userService.updateUser(request, userDTO);
        return ResponseEntity.ok().body("User update completed");
    }
}
