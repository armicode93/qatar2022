package com.example.qatar2022.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class HomeController {



    /*@GetMapping("/")
    public String home() {
        return "index";
    }

     */
    // if redirect is not null we are going to pass the URL
    @GetMapping("/login")
    public String login(@RequestParam(name = "redirect", required = false) String redirect) {
        if (redirect != null && !redirect.isEmpty()) {
            return "redirect:" + redirect;
        }
        return "login";
    }


    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}

