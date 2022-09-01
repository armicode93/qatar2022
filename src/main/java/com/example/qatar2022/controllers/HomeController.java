package com.example.qatar2022.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login(Model model) {

        return "login";
    }

    /*@GetMapping("/")
    public String home() {
        return "index";
    }

     */



    @GetMapping("/4003")
    public String accessDenied() {
        return "403";
    }
}

