package com.furreverhome.Furrever_Home.validation;

import com.google.common.base.Joiner;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.passay.*;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    /**
     * Initializes the constraint validator.
     *
     * @param arg0 The annotation instance for the constraint.
     */
    @Override
    public void initialize(final ValidPassword arg0) {
    }

    /**
     * Validates whether the given password meets the defined criteria.
     *
     * @param password The password to be validated.
     * @param context  The context in which the constraint is evaluated.
     * @return         {@code true} if the password is valid, {@code false} otherwise.
     */
    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        // @formatter:off
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(8, 30));
        rules.add(new UppercaseCharacterRule(1));
        rules.add(new DigitCharacterRule(1));
        rules.add(new SpecialCharacterRule(1));
        rules.add(new NumericalSequenceRule(3, false));
        rules.add(new AlphabeticalSequenceRule(3, false));
        rules.add(new QwertySequenceRule(3, false));
        rules.add(new WhitespaceRule());


        final PasswordValidator validator = new PasswordValidator(rules);
        final RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result))).addConstraintViolation();
        return false;
    }

}
