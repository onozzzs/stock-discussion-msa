package com.example.activity.api;

import com.example.activity.dto.FollowDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "followAPI", url = "http://localhost:8080/api/follow/internal")
public interface FollowAPI {
    @RequestMapping(method = RequestMethod.GET, value = "/getFollowings")
    List<FollowDTO> getFollowings(String userId);

    @RequestMapping(method = RequestMethod.GET, value = "/getFollowers")
    List<FollowDTO> getFollowers(String userId);
}
