package com.xxx.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping(value = "/employee/basic/hellO")
    public String hello2() {
        return "hello";
    }

    @GetMapping(value = "/employee/advanced/hello")
    public String hello3() {
        return "hello";
    }
}
