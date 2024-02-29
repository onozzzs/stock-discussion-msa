package com.example.activity.api;

import com.example.activity.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userAPI", url = "http://localhost:8080/api/internal/user")
public interface UserAPI {
    @RequestMapping(method = RequestMethod.GET, value = "/getUserByUsername")
    UserDTO getUserByUsername(@RequestParam(value = "username") String username);

    @RequestMapping(method = RequestMethod.GET, value = "/getUserByUserId")
    UserDTO getUserByUserId(@RequestParam(value = "userId") String userId);
}
