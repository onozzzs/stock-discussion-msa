package com.example.activity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activity/test")
public class ActivityTestController {
    @GetMapping
    public String test() {
        return "ActivityTest";

    }
}
