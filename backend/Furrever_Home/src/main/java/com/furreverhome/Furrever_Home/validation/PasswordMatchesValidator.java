package com.furreverhome.Furrever_Home.validation;

import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    /**
     * Initializes the validator.
     *
     * @param constraintAnnotation The annotation instance for the constraint being validated.
     */
    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    /**
     * Validates whether the new password and its verification match.
     *
     * @param obj      The object to be validated, which should be a PasswordDto instance.
     * @param context  The context in which the constraint is evaluated.
     * @return         {@code true} if the new password matches its verification, {@code false} otherwise.
     */
    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final PasswordDto passwordDto = (PasswordDto) obj;
        return passwordDto.getNewPassword().equals(passwordDto.getVerifyNewPassword());
    }

}
