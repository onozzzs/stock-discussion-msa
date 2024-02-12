package com.example.newsfeed.api;

import com.example.newsfeed.dto.ActivityDTO;
import com.example.user.dto.ActivityRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "activityAPI", url = "http://localhost:8082/api/activity/internal")
public interface ActivityAPI {
    @RequestMapping(method = RequestMethod.GET, value = "/getActivities")
    List<ActivityDTO> getActivities(String userId);

    @RequestMapping(method = RequestMethod.POST, value = "/updateActivity")
    void makeAndSaveActivity(ActivityRequestDTO activityRequestDTO);
}
