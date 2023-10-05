package com.example.qatar2022.config.annotation;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(
    validatedBy =
        UniqueUsernameValidator
            .class) // restriction of validation personalisee, that its mean that this classe will
// be used to do the logique of validation personalized

@Target({
  ElementType.FIELD,
  ElementType.METHOD
}) // i will specify the type of elements where the annotation can be used, inb this case will be
// used in field and method

@Retention(
    RetentionPolicy
        .RUNTIME) // la durée de conservation de l'annotation @UniqueUsername. Dans ce cas,
// l'annotation sera conservée jusqu'au moment de l'exécution.

public @interface UniqueUsername {
  String message() default "Invalid username";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
