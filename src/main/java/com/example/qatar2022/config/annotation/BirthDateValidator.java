package com.example.qatar2022.config.annotation;

import java.time.LocalDate;
import java.time.Period;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {
  @Override
  public boolean isValid(
      final LocalDate valueToValidate, final ConstraintValidatorContext context) {
    LocalDate currentDate = LocalDate.now();
    Period period = Period.between(valueToValidate, currentDate);

    return period.getYears() >= 18;
  }
}
