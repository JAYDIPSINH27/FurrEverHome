package com.furreverhome.Furrever_Home.services.authenticationServices;

import com.furreverhome.Furrever_Home.dto.auth.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.auth.SigninRequest;
import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.dto.auth.SignupRequest;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    boolean signup(String appUrl, SignupRequest signupRequest) throws MessagingException;
    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    GenericResponse resetByEmail(final String contextPath, String email);

    GenericResponse resetPassword(final PasswordDto passwordDto);

    String validatePasswordResetToken(final String token);

    GenericResponse updateUserPassword(final PasswordDto passwordDto);

    boolean verifyByEmail(String email);
}
