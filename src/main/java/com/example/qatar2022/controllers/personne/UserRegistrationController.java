package com.example.qatar2022.controllers.personne;


import com.example.qatar2022.dto.UserRegistrationDto;
import com.example.qatar2022.repository.personne.UserRepository;
import com.example.qatar2022.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller

@CrossOrigin(origins = "*")

public class UserRegistrationController {


    @Autowired
    private UserService userService; //interface implementation
    @Autowired
    private UserRepository userRepository;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }


    @ModelAttribute("user")

    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }



    @GetMapping("/registration")
    public String registrationForm() {
        return "registration";
    }


    // Valid is for activate the validation
    //Biinding Result give me access at the result
    @PostMapping("/registration/new")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }
        if (userService.loadUserByUsername(registrationDto.getUsername()) != null) {
            result.rejectValue("username", "error.user");
            return "registration";
        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            result.rejectValue("email", "error.email");
            return "registration";
        }
        userService.save(registrationDto);
        return "redirect:/login?success";
    }



}




