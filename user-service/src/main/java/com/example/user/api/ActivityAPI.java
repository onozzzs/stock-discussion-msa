package com.example.user.api;

import com.example.user.dto.ActivityRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="activityAPI", url = "http://localhost:8082/api/activity/internal")
public interface ActivityAPI {
    @RequestMapping(method = RequestMethod.POST, value = "/updateActivity")
    void updateActivity(ActivityRequestDTO activityRequestDTO);
}
