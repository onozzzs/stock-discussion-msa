package com.example.activity.api;

import com.example.activity.dto.NotificationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "notificationAPI", url = "http://localhost:8081/api/notification")
public interface NotificationAPI {
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    void saveNotification(NotificationRequestDTO notificationRequestDTO);
}
