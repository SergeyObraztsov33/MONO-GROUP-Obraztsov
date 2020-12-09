package com.example.demo.controller;

import com.example.demo.DemoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    private static Logger log = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/world")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        log.error("test");
        return String.format("Hello %s!", name);
    }
}
