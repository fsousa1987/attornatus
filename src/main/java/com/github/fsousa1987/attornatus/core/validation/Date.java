package com.github.fsousa1987.attornatus.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Date {
    String message() default "Invalid date format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
