package com.furreverhome.Furrever_Home.unittests.services.authenticationServices;

import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.dto.auth.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.auth.PetAdopterSignupRequest;
import com.furreverhome.Furrever_Home.dto.auth.ShelterSignupRequest;
import com.furreverhome.Furrever_Home.dto.auth.SigninRequest;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import com.furreverhome.Furrever_Home.entities.PasswordResetToken;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.PasswordTokenRepository;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.services.authenticationServices.AuthenticationServiceImpl;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.jwtservices.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private PetAdopterRepository petAdopterRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordTokenRepository passwordTokenRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("user@example.com");
        mockUser.setPassword(passwordEncoder.encode("password"));
        mockUser.setVerified(true);
    }

    /**
     * Tests the successful signup of a pet adopter.
     *
     * @throws MessagingException if an error occurs while sending an email
     */
    @Test
    void testSuccessfulPetAdopterSignup() throws MessagingException {
        // Arrange
        PetAdopterSignupRequest signupRequest = new PetAdopterSignupRequest();

        signupRequest.setEmail("johndoe@example.com");
        signupRequest.setPassword("password123");
        signupRequest.setRole("PET_ADOPTER");
        signupRequest.setCheckRole(1);
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPhone_number("1234567890");
        signupRequest.setAddress("1234 Maple Street");
        signupRequest.setCity("Springfield");
        signupRequest.setCountry("Neverland");
        signupRequest.setZipcode("98765");

        String encodedPassword = "encodedPassword";
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn(encodedPassword);
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString(), eq(true));

        // Act
        boolean result = authenticationService.signup("http://localhost:8080", signupRequest);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
        verify(petAdopterRepository, times(1)).save(any(PetAdopter.class));
        verify(emailService, times(1)).sendEmail(
                eq(signupRequest.getEmail()),
                anyString(),
                anyString(),
                eq(true));
    }

    /**
     * Tests the successful signup of a shelter.
     *
     * @throws Exception if an error occurs
     */
    @Test
    void testSuccessfulShelterSignup() throws Exception {
        //Arange
        ShelterSignupRequest signupRequest = new ShelterSignupRequest();
        signupRequest.setEmail("shelter@example.com");
        signupRequest.setPassword("securePassword");
        signupRequest.setCheckRole(2);

        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("encodedPassword");
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString(), eq(true));

        // Act
        boolean result = authenticationService.signup("http://localhost:8080", signupRequest);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
        verify(shelterRepository, times(1)).save(any(Shelter.class));
        verify(emailService, times(1)).sendEmail(
                eq(signupRequest.getEmail()),
                anyString(),
                anyString(),
                eq(true));
    }

    /**
     * Tests signup with an existing email address.
     */
    @Test
    void testSignupWithExistingEmail() {
        // Arrange
        PetAdopterSignupRequest signupRequest = new PetAdopterSignupRequest();
        signupRequest.setEmail("existingemail@example.com");
        signupRequest.setPassword("password");

        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authenticationService.signup("http://localhost:8080", signupRequest);
        });

        String expectedMessage = "User Already Exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Tests the successful sign-in process for a pet adopter.
     * It verifies that the authentication service correctly generates JWT and refresh tokens.
     */
    @Test
    void testSigninSuccessForPetAdopter() {
        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setEmail("user@example.com");
        signinRequest.setPassword("password");
        when(userRepository.findByEmail(signinRequest.getEmail())).thenReturn(Optional.of(mockUser));
        when(shelterRepository.findByUserId(mockUser.getId())).thenReturn(Optional.ofNullable(null));
        when(petAdopterRepository.findByUserId(mockUser.getId())).thenReturn(Optional.ofNullable(mock(PetAdopter.class)));
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
        when(jwtService.generateToken(any(User.class))).thenReturn("mockJwtToken");
        when(jwtService.generateRefreshToken(any(), any(User.class))).thenReturn("mockRefreshToken");

        // Act
        JwtAuthenticationResponse response = authenticationService.signin(signinRequest);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals("mockJwtToken", response.getToken(), "JWT token does not match expected value");
        assertEquals("mockRefreshToken", response.getRefreshToken(), "Refresh token does not match expected value");

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(signinRequest.getEmail());
        verify(shelterRepository, times(2)).findByUserId(mockUser.getId());
        verify(petAdopterRepository).findByUserId(mockUser.getId());
        verify(jwtService).generateToken(mockUser);
        verify(jwtService).generateRefreshToken(anyMap(), eq(mockUser));
    }

    /**
     * Tests sign-in with bad credentials.
     * It verifies that the authentication service correctly throws a BadCredentialsException.
     */
    @Test
    void testSigninWithBadCredentials() {
        // Arrange
        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setEmail("user@example.com");
        signinRequest.setPassword("password");
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new BadCredentialsException("Incorrect username or password"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authenticationService.signin(signinRequest), "Expected BadCredentialsException to be thrown");

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtService);
    }

    /**
     * Tests sign-in with an unverified user.
     * It verifies that the authentication service correctly handles unverified users.
     */
    @Test
    void signinWithUnverifiedUser() {
        mockUser.setVerified(false);
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUser));
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));

        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setEmail("user@example.com");
        signinRequest.setPassword("password");

        JwtAuthenticationResponse response = authenticationService.signin(signinRequest);

        assertFalse(response.getVerified());
    }

    /**
     * Tests verifying a user by email when the user exists and is verified.
     * It verifies that the authentication service correctly updates the user's verification status.
     */
    @Test
    void testVerifyByEmailUserExistsUserVerified() {
        // Arrange
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);
        user.setVerified(false);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        boolean result = authenticationService.verifyByEmail(email);

        // Assert
        assertTrue(result);
        assertTrue(user.getVerified());
        verify(userRepository).save(user);
    }

    /**
     * Tests the verification process for a non-existing user.
     */
    @Test
    void testVerifyByEmailUserDoesNotExistReturnFalse() {
        // Arrange
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        boolean result = authenticationService.verifyByEmail(email);

        // Assert
        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Tests the successful password reset process.
     *
     * @throws Exception if an error occurs during password reset
     */
    @Test
    void testResetByEmailSuccessfulReset() throws Exception {
        // Arrange
        String email = "user@example.com";
        String token = "randomToken";
        String contextPath = "http://example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        when(jwtService.generateToken(mockUser)).thenReturn(token);

        // Act
        GenericResponse response = authenticationService.resetByEmail(contextPath, email);

        // Assert
        assertNotNull(response);
        assertTrue(response.getMessage().contains("A password reset email has been sent"));
        verify(jwtService).generateToken(mockUser);
        verify(passwordTokenRepository).save(any(PasswordResetToken.class));
        verify(emailService).sendEmail(eq(email), eq("Password Reset"), anyString(), eq(true));
    }

    /**
     * Tests the password reset process when the user is not found.
     */
    @Test
    void testResetByEmailUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        String contextPath = "http://example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authenticationService.resetByEmail(contextPath, email);
        }, "Expected exception for user not found");
    }

    /**
     * Tests the reset password process with an invalid token.
     */
    @Test
    void testResetPasswordWithInvalidTokenReturnsErrorMessage() {
        // Arrange
        String token = "invalidToken";
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setToken(token);
        passwordDto.setNewPassword("newPassword");
        when(authenticationService.validatePasswordResetToken(token)).thenReturn(null);

        // Act
        GenericResponse response = authenticationService.resetPassword(passwordDto);

        // Assert
        assertEquals(token,response.getMessage());
    }

    /**
     * Tests the creation of an admin account when no admin account exists.
     */
    @Test
    public void testWhenNoAdminAccountExistsThenCreateAdminAccount() {
        // Arrange
        when(userRepository.findByRole(Role.ADMIN)).thenReturn(null);

        // Act
        authenticationService.createAdminAccount();

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Tests the creation of an admin account when an admin account already exists.
     */
    @Test
    public void testWhenAdminAccountExistsThenDoNotCreateAdminAccount() {
        // Arrange
        User existingAdmin = new User();
        existingAdmin.setEmail("admin@gmail.com");
        existingAdmin.setRole(Role.ADMIN);
        when(userRepository.findByRole(Role.ADMIN)).thenReturn(existingAdmin);

        // Act
        authenticationService.createAdminAccount();

        // Assert
        verify(userRepository, never()).save(any(User.class));
    }

//    @Test
//    void resetPassword_WithValidTokenAndUserPresent_PasswordResetSuccessfully() {
//        // Arrange
//        String token = "validToken";
//        PasswordDto passwordDto = new PasswordDto();
//        passwordDto.setNewPassword("newPassword");
//
//        when(authenticationService.validatePasswordResetToken(token)).thenReturn(null);
//        when(authenticationService.getUserByPasswordResetToken(token)).thenReturn(Optional.of(mockUser));
//        // Simulating finding a user
//
//        // Act
//        GenericResponse response = authenticationService.resetPassword(passwordDto);
//
//        // Assert
//        assertEquals("Password reset successfully", response.getMessage());
//    }

//    @Test
//    void resetPassword_WithValidTokenButNoUser_ReturnsErrorMessage() {
//        // Arrange
//        String token = "validTokenButNoUser";
//        PasswordDto passwordDto = new PasswordDto();
//        passwordDto.setToken("validTokenButNoUser");
//        when(authenticationService.validatePasswordResetToken(passwordDto.getToken())).thenReturn(null);
//        when(authenticationService.getUserByPasswordResetToken(passwordDto.getToken())).thenReturn();
//        PasswordResetToken validToken = new PasswordResetToken(token, new User());
//        when(passwordTokenRepository.findByToken(token)).thenReturn(validToken);
//        // Act
//        GenericResponse response = authenticationService.resetPassword(passwordDto);
//
//        // Assert
//        assertNotNull(response.getMessage());
//        assertEquals("This username is invalid, or does not exist", response.getMessage());
//    }
}
