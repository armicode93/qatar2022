package com.example.qatar2022.controllers.personne;

import com.example.qatar2022.dto.UserRegistrationDto;
import com.example.qatar2022.repository.personne.UserRepository;
import com.example.qatar2022.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*")
public class UserRegistrationController {

  @Autowired private UserService userService; // interface implementation
  @Autowired private UserRepository userRepository;

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
  // Biinding Result give me access to the result
  @PostMapping("/registration")
  public String registerUserAccount(
      @Valid @ModelAttribute("user") UserRegistrationDto registrationDto,
      BindingResult result,
      Model model) {

    if (result.hasErrors()) {
      return "registration";
    }

    userService.save(registrationDto);

    model.addAttribute("user", registrationDto);

    return "redirect:/login?success";
  }
}
