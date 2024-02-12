package com.example.newsfeed.api;

import com.example.newsfeed.dto.FollowDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "followAPI", url = "http://localhost:8080/api/follow/internal")
public interface FollowAPI {
    @RequestMapping(method = RequestMethod.GET, value = "/getFollowings")
    List<FollowDTO> getFollowings(String userId);
}
