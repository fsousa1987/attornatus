package com.github.fsousa1987.attornatus.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CustomDateValidator implements ConstraintValidator<Date, String> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public void initialize(Date customDate) {
        ConstraintValidator.super.initialize(customDate);
    }

    @Override
    public boolean isValid(String customDateField, ConstraintValidatorContext ctx) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        try {
            sdf.setLenient(false);
            sdf.parse(customDateField);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
