package net.example.plantsearchrest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]+$";

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null &&
                value.length() >= MIN_USERNAME_LENGTH &&
                value.length() <= MAX_USERNAME_LENGTH &&
                value.matches(USERNAME_PATTERN);
    }
}