package com.example.user.api;

import com.example.user.dto.ActivityRequestDTO;
import com.example.user.dto.FollowDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="activityAPI", url = "http://localhost:8082/api/internal/activity")
public interface ActivityAPI {
    @RequestMapping(method = RequestMethod.POST, value = "/saveActivity")
    void saveActivity(@RequestBody FollowDTO followDTO);
}
