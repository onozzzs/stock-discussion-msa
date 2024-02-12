package com.example.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="newsfeedClient", url = "http://localhost:8081/api/newsfeed")
public interface NewsfeedAPI {
    @RequestMapping(method = RequestMethod.GET, value = "/test")
    String test();
}
