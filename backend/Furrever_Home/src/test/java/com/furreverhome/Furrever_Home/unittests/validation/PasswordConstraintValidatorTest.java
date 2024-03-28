package com.furreverhome.Furrever_Home.unittests.validation;

import com.furreverhome.Furrever_Home.validation.PasswordConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link PasswordConstraintValidator} class.
 */
class PasswordConstraintValidatorTest {

    private PasswordConstraintValidator passwordConstraintValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        passwordConstraintValidator = new PasswordConstraintValidator();
        context = mock(ConstraintValidatorContext.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(mock(ConstraintValidatorContext.ConstraintViolationBuilder.class));
    }

    @Test
    void testValidPassword() {
        assertTrue(passwordConstraintValidator.isValid("Valid1@2", context));
    }

    @Test
    void testInvalidPasswordTooShort() {
        assertFalse(passwordConstraintValidator.isValid("V1@", context));
    }

    @Test
    void testInvalidPasswordNoUppercase() {
        assertFalse(passwordConstraintValidator.isValid("valid1@", context));
    }

    @Test
    void testInvalidPasswordNoDigit() {
        assertFalse(passwordConstraintValidator.isValid("Valid@", context));
    }

    @Test
    void testInvalidPasswordNoSpecialCharacter() {
        assertFalse(passwordConstraintValidator.isValid("Valid1", context));
    }

    @Test
    void testInvalidPasswordContainsWhitespace() {
        assertFalse(passwordConstraintValidator.isValid("Valid 1@", context));
    }

    @Test
    void testInvalidPasswordSequentialNumeric() {
        assertFalse(passwordConstraintValidator.isValid("Valid123@", context));
    }

    @Test
    void testInvalidPasswordSequentialAlphabetical() {
        assertFalse(passwordConstraintValidator.isValid("Validabc@", context));
    }

    @Test
    void testInvalidPasswordSequentialQwerty() {
        assertFalse(passwordConstraintValidator.isValid("Validqwe@", context));
    }

}
