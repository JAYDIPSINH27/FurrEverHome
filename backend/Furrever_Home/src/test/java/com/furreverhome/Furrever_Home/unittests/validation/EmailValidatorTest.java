package com.furreverhome.Furrever_Home.unittests.validation;

import com.furreverhome.Furrever_Home.validation.EmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link EmailValidator} class.
 */
class EmailValidatorTest {

    private EmailValidator emailValidator;

    @BeforeEach
    void setUp() {
        emailValidator = new EmailValidator();
    }

    @Test
    void testValidEmail() {
        assertTrue(emailValidator.isValid("email@example.com", null));
    }

    @Test
    void testInvalidEmail_NoTld() {
        assertFalse(emailValidator.isValid("email@example", null));
    }

    @Test
    void testInvalidEmail_MissingAtSymbol() {
        assertFalse(emailValidator.isValid("emailexample.com", null));
    }

    @Test
    void testValidEmail_SubDomain() {
        assertTrue(emailValidator.isValid("email@sub.example.com", null));
    }

    @Test
    void testInvalidEmail_DoubleDot() {
        assertFalse(emailValidator.isValid("email@example..com", null));
    }

    @Test
    void testValidEmail_WithPlus() {
        assertTrue(emailValidator.isValid("firstname+lastname@example.com", null));
    }

    @Test
    void testInvalidEmail_MissingUsername() {
        assertFalse(emailValidator.isValid("@example.com", null));
    }

    @Test
    void testEmptyEmail() {
        assertFalse(emailValidator.isValid("", null));
    }
}
