package com.example.qatar2022.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeController {



    /*@GetMapping("/")
    public String home() {
        return "index";
    }

     */

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }


    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}

