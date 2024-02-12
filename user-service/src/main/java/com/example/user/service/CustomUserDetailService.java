package com.example.user.service;

import com.example.user.dto.UserDTO;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByMail(username);
        validate(user);
        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), true, true, true, true, new ArrayList<>());
    }

    public UserDTO getUserDetailsByMail(String mail) {
        User user = userRepository.findByMail(mail);
        validate(user);

        return UserDTO.builder()
                .id(user.getId())
                .mail(user.getMail())
                .password(user.getPassword())
                .build();
    }

    private void validate(User user) {
        if (user == null) {
            throw new UsernameNotFoundException(user.getMail());
        }
    }
}
