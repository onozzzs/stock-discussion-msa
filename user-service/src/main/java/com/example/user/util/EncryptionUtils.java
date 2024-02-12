package com.example.user.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncryptionUtils {
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String data) {
        return passwordEncoder.encode(data);
    }

    public static boolean validatePassword(String password, String encrypted) {
        return passwordEncoder.matches(password, encrypted);
    }
}
