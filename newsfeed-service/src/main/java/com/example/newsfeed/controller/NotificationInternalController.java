package com.example.newsfeed.controller;

import com.example.newsfeed.dto.NotificationRequestDTO;
import com.example.newsfeed.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification/internal")
public class NotificationInternalController {
    @Autowired
    NotificationService notificationService;

    @PostMapping("/save")
    public void saveNotification(@RequestBody NotificationRequestDTO notificationRequestBody) {
        notificationService.save(notificationRequestBody);
    }
}
