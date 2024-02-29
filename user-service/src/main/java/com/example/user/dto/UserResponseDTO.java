package com.example.user.dto;

import com.example.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponseDTO {
    private String token;
    private String mail;
    private String username;
    private String password;
    private String id;
    private String content;
    private boolean status;

    public UserResponseDTO(String id, String mail, String password, String username, boolean status) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.username = username;
        this.status = status;
    }

    public static User toEntity(final UserResponseDTO dto) {
        return User.builder()
                .mail(dto.getMail())
                .username(dto.getUsername())
                .content(dto.getContent())
                .build();
    }
}
