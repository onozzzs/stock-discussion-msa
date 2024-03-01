package com.example.user.model;

import com.example.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="follow_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="following")
    private User following;
    @ManyToOne
    @JoinColumn(name="follower")
    private User follower;
}
