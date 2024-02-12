package com.example.user.controller.auth;

import com.example.user.dto.UserDTO;
import com.example.user.model.User;
import com.example.user.service.AuthService;
import com.example.user.service.MailService;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/auth")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/mail/send")
    public String sendMail(@RequestParam("mail") String mail) {
        return mailService.makeAndSendMail(mail);
    }

    @PostMapping("/mail/verify")
    public ResponseEntity<?> verifyMail(@RequestParam("mail") String mail,
                                        @RequestParam("auth") String auth) {
        User changedUser = mailService.checkMail(mail, auth);
        UserDTO responseUserDTO = new UserDTO(changedUser.getId(), changedUser.getMail(), changedUser.getPassword(), changedUser.getUsername(), changedUser.isStatus());
        return ResponseEntity.ok().body(responseUserDTO);
    }
}
