package com.example.stock.api;

import com.example.stock.dto.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
//
//@FeignClient(name="postAPI", url = "http://localhost:8082/api/post/internal")
//public class PostAPI {
//    @RequestMapping(method = RequestMethod.GET, value = "/getPost")
//    List<PostDTO> getPost(String ticker);
//}
