package com.example.qatar2022.config.annotation;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = BirthDateValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDate {
  String message() default "Invalid date birth";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
