package com.example.activity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class UserDTO {
    private String mail;
    private String username;
    private String id;

    public UserDTO(String id, String mail, String username) {
        this.id = id;
        this.mail = mail;
        this.username = username;
    }

}
