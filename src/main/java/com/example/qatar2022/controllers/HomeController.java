package com.example.qatar2022.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//annotation pour cree API restFul
public class HomeController {

    @RequestMapping("/")
    public String HomeController()
    {
        return "Welcome Back";
    }
}
