package com.example.mentalHealthApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping(path = "/hello")
    public  String helloWorld(){
        return "Hello World";
    }
}
