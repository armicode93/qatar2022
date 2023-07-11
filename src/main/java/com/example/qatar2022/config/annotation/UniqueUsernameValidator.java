package com.example.qatar2022.config.annotation;

import com.example.qatar2022.service.UserService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

  private final UserService userService;

  public UniqueUsernameValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void initialize(UniqueUsername constraintAnnotation) {
    // Nessuna inizializzazione necessaria
  }

  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    return userService.loadUserByUsername(username) == null;
  }
}
