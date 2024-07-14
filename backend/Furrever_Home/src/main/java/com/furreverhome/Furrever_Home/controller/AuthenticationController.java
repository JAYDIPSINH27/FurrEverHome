package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.auth.SignupRequest;
import com.furreverhome.Furrever_Home.services.jwtservices.JwtService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;
import com.furreverhome.Furrever_Home.config.FrontendConfigurationProperties;
import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.auth.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.auth.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.auth.SigninRequest;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import com.furreverhome.Furrever_Home.dto.user.ResetEmailRequest;
import com.furreverhome.Furrever_Home.services.authenticationServices.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final FrontendConfigurationProperties frontendConfigurationProperties;

    /**
     * Handles user signup.
     * @param request The HTTP servlet request.
     * @param signupRequest The signup request object.
     * @return ResponseEntity containing the result of the signup operation.
     * @throws MessagingException if an error occurs during email sending.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(HttpServletRequest request, @RequestBody SignupRequest signupRequest) throws MessagingException {
        return ResponseEntity.ok(authenticationService.signup(getAppUrl(request), signupRequest));
    }

    /**
     * Handles email verification.
     * @param email The email to verify.
     * @return RedirectView to the login page if successful, otherwise ResponseEntity.notFound().
     */
    @GetMapping("/verify/{email}")
    public Object verifyByEmail(@PathVariable String email) {
        if (authenticationService.verifyByEmail(email)) {
            return new RedirectView(frontendConfigurationProperties.getLoginUrl());
        }
        return ResponseEntity.notFound();
    }

    /**
     * Handles user signin.
     * @param signinRequest The signin request object.
     * @return ResponseEntity containing the JWT authentication response.
     */
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin (@RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    /**
     * Handles token refresh.
     * @param refreshTokenRequest The refresh token request object.
     * @return ResponseEntity containing the JWT authentication response.
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh (@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(jwtService.refreshToken(refreshTokenRequest));
    }

    /**
     * Handles password reset request.
     * @param request The HTTP servlet request.
     * @param resetEmailRequest The reset email request object.
     * @return ResponseEntity containing the result of the password reset operation.
     */
    @PostMapping("/forgetPassword")
    public ResponseEntity<GenericResponse> reset (
        final HttpServletRequest request,
        @Valid @RequestBody ResetEmailRequest resetEmailRequest
    ) {
        return ResponseEntity.ok(authenticationService.resetByEmail(getAppUrl(request), resetEmailRequest.email()));
    }

    /**
     * Redirects to the change password page.
     * @param token The reset password token.
     * @return RedirectView to the appropriate page based on token validation result.
     */
    @GetMapping("/redirectChangePassword")
    public RedirectView showChangePasswordPage(@RequestParam("token") String token) {
        String result = authenticationService.validatePasswordResetToken(token);
        if(result != null) {
            String message;
            switch (result) {
                case "invalidToken":
                    message = "Invalid token.";
                    break;
                case "expired":
                    message = "Token has expired.";
                    break;
                default:
                    message = "Error.";
            }

          String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
          return new RedirectView(frontendConfigurationProperties.getLoginUrl() + "?message="  + encodedMessage);
        } else {
            return new RedirectView(frontendConfigurationProperties.getUpdatePasswordUrl() + "?token=" + token);
        }
    }

    /**
     * Handles password reset.
     * @param passwordDto The password DTO object.
     * @return GenericResponse containing the result of the password reset operation.
     */
    @PostMapping("/resetPassword")
    public GenericResponse resetPassword(@Valid @RequestBody PasswordDto passwordDto) {
        return authenticationService.resetPassword(passwordDto);
    }

    /**
     * Handles password update.
     * @param passwordDto The password DTO object.
     * @return GenericResponse containing the result of the password update operation.
     */
    @PostMapping("/updatePassword")
    public GenericResponse changeUserPassword(@Valid @RequestBody PasswordDto passwordDto) {
        return authenticationService.updateUserPassword(passwordDto);
    }

    /**
     * Retrieves the application URL.
     * @param request The HTTP servlet request.
     * @return The application URL.
     */
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
