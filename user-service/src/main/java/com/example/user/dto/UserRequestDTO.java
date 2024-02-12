package com.example.user.dto;

import com.example.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email must be grater than 2 characters")
    @Email
    private String mail;

    @Size(min = 2, message = "Password must be grater than 2 characters")
    @NotNull(message = "Password cannot be null")
    private String password;

    @Size(min = 2, message = "Name must be grater than 2 characters")
    private String username;

    public User toEntity(UserRequestDTO userRequestDTO) {
        return User.builder()
                .mail(userRequestDTO.getMail())
                .password(userRequestDTO.getPassword())
                .username(userRequestDTO.getUsername())
                .build();
    }
}