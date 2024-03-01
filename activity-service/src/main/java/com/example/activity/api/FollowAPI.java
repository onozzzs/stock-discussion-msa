package com.example.activity.api;

import com.example.activity.dto.FollowDTO;
import com.example.activity.dto.FollowingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "followAPI", url = "http://localhost:8080/api/internal/user/follow")
public interface FollowAPI {
    @RequestMapping(method = RequestMethod.GET, value = "/getFollowings")
    List<FollowingDTO> getFollowings(@RequestParam(value = "userId") String userId);

    @RequestMapping(method = RequestMethod.GET, value = "/getFollowers")
    List<FollowDTO> getFollowers(@RequestParam(value = "userId") String userId);
}
