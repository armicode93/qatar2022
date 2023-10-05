package com.example.qatar2022.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

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
