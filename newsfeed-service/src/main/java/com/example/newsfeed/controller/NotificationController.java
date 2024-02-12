package com.example.newsfeed.controller;

import com.example.newsfeed.dto.NotificationRequestDTO;
import com.example.newsfeed.dto.NotificationResponseDTO;
import com.example.newsfeed.model.Notification;
import com.example.newsfeed.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> getNotification(HttpServletRequest request) {
        List<Notification> notifications = notificationService.getNotification(request);
        List<NotificationResponseDTO> responseDTOs = notifications.stream().map(NotificationResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(responseDTOs);
    }

    @GetMapping("/toUser")
    public ResponseEntity<?> getNotificationToUser(HttpServletRequest request) {
        List<Notification> notifications = notificationService.getNotificationToUser(request);
        List<NotificationResponseDTO> responseDTOs = notifications.stream().map(NotificationResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(responseDTOs);
    }
}
