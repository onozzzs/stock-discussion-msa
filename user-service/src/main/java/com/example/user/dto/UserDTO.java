package com.example.user.dto;

import com.example.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String mail;
    private String username;
    private String id;

    public UserDTO(User user) {
        this.id = user.getId();
        this.mail = user.getMail();
        this.username = user.getUsername();
    }

    public static User toEntity(final UserDTO dto) {
        return User.builder()
                .mail(dto.getMail())
                .username(dto.getUsername())
                .build();
    }
}
