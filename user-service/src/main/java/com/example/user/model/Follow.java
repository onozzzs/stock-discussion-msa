package com.example.user.model;

import com.example.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="follow_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="following")
    private User following;
    @ManyToOne
    @JoinColumn(name="follower")
    private User follower;
}
