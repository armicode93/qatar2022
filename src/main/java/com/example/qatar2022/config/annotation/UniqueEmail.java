package com.example.qatar2022.config.annotation;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
  String message() default "Invalid email";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
