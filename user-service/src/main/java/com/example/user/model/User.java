package com.example.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.lang.Nullable;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @UuidGenerator
    @Column(name="user_id")
    private String id;

    @Column(nullable = false, length = 50, unique = true)
    private String mail;

    @Column(nullable = false, unique = true)
    private String password;

    private String username;
    private String content;
    private String profileUrl;
    private boolean status;

    @OneToMany(mappedBy = "follower")
    private List<Follow> followingList;

    @OneToMany(mappedBy = "following")
    private List<Follow> followerList;

//    @Nullable
//    @OneToMany(mappedBy = "user")
//    private List<Activity> activityList;

    public void updateStatus() {
        this.status = true;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void updateUsernameAndContent(String username, String content) {
        this.username = username;
        this.content = content;
    }
}
