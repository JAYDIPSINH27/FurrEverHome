package com.furreverhome.Furrever_Home.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    /**
     * Validates whether the given email address follows the standard email format.
     *
     * @param username   The username (email) address to be validated.
     * @param context The context in which the constraint is evaluated.
     * @return        {@code true} if the email address is valid, {@code false} otherwise.
     */
    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        return (validateEmail(username));
    }

    /**
     * Validates the given email address against the email pattern.
     *
     * @param email The email address to be validated.
     * @return      {@code true} if the email address matches the pattern, {@code false} otherwise.
     */
    private boolean validateEmail(final String email) {
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }
}
