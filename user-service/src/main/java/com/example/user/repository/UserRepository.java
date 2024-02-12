package com.example.user.repository;

import com.example.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findByMail(String mail);
    User findByMailAndPassword(String mail, String password);
    Boolean existsByUsername(String username);
    Boolean existsByMail(String mail);

}
