package com.checkpoint.StudentsCourses.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuestController {
    @GetMapping
    public String hello() {
        return "Hello Guest!";
    }
}
