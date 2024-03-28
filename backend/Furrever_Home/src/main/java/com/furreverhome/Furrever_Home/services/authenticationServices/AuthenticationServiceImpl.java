package com.furreverhome.Furrever_Home.services.authenticationServices;


import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.dto.auth.*;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import com.furreverhome.Furrever_Home.entities.PasswordResetToken;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.exception.EmailExistsException;
import com.furreverhome.Furrever_Home.repository.PasswordTokenRepository;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.jwtservices.JwtService;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordTokenRepository passwordTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final EmailService emailService;

    private final ShelterRepository shelterRepository;

    private final PetAdopterRepository petAdopterRepository;

    /**
     * Creates an admin account if one does not already exist.
     */
    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(Role.ADMIN);

        if(adminAccount == null) {
            User user = new User();

            user.setEmail("admin@gmail.com");
            user.setRole(Role.ADMIN);
            user.setVerified(Boolean.TRUE);
            user.setPassword(new BCryptPasswordEncoder().encode("Jp@32padhiyar"));
            userRepository.save(user);
            System.out.println("Admin successfully created..");
        }
    }

    /**
     * Registers a new user (either a shelter or a pet adopter) and sends a verification email.
     *
     * @param appUrl The base URL of the application.
     * @param signupRequest The request containing the signup details.
     * @return true if the signup process is successful, false otherwise.
     * @throws MessagingException if an error occurs while sending the email.
     */
    public boolean signup(String appUrl, SignupRequest signupRequest) throws MessagingException {
        int shelterCheckRole = 2;
        int petAdopterCheckRole = 1;

        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            try {
                throw new EmailExistsException("User Already Exists");
            } catch (EmailExistsException e) {
                throw new RuntimeException(e);
            }
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setVerified(Boolean.FALSE);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        if (signupRequest.getCheckRole() == petAdopterCheckRole) {
            PetAdopter petAdopter = new PetAdopter();

            petAdopter.setFirstname(((PetAdopterSignupRequest) signupRequest).getFirstName());
            petAdopter.setLastname(((PetAdopterSignupRequest) signupRequest).getLastName());
            petAdopter.setPhone_number(((PetAdopterSignupRequest) signupRequest).getPhone_number());
            petAdopter.setAddress(((PetAdopterSignupRequest) signupRequest).getAddress());
            petAdopter.setCity(((PetAdopterSignupRequest) signupRequest).getCity());
            petAdopter.setCountry(((PetAdopterSignupRequest) signupRequest).getCountry());
            petAdopter.setZipcode(((PetAdopterSignupRequest) signupRequest).getZipcode());

            user.setRole(Role.PETADOPTER);
            User result = userRepository.save(user);
            petAdopter.setUser(result);

            petAdopterRepository.save(petAdopter);
        } else if (signupRequest.getCheckRole() == shelterCheckRole){
            Shelter shelter = new Shelter();
            shelter.setName(((ShelterSignupRequest) signupRequest).getName());
            shelter.setContact(((ShelterSignupRequest) signupRequest).getContact());
            shelter.setLicense(((ShelterSignupRequest) signupRequest).getLicense());
            shelter.setCapacity(((ShelterSignupRequest) signupRequest).getCapacity());
            shelter.setImageBase64(((ShelterSignupRequest) signupRequest).getImageBase64());
            shelter.setCity(((ShelterSignupRequest) signupRequest).getCity());
            shelter.setCountry(((ShelterSignupRequest) signupRequest).getCountry());
            shelter.setAddress(((ShelterSignupRequest) signupRequest).getAddress());
            shelter.setZipcode(((ShelterSignupRequest) signupRequest).getZipcode());
            shelter.setRejected(Boolean.FALSE);

            user.setRole(Role.SHELTER);
            User result = userRepository.save(user);
            shelter.setUser(result);

            shelterRepository.save(shelter);
        } else {
            throw new RuntimeException("Registration details is incorrect.");
        }

        String url = appUrl + "/api/auth/verify/" + signupRequest.getEmail();
        String linkText = "Click here to verify your email.";
        String message = "<p>Please use the link below to verify your email.</p>"
                + "<a href=\"" + url + "\">" + linkText + "</a>";
        // TODO: Separate the logic for the code to send email from here.
        emailService.sendEmail(signupRequest.getEmail(), "Email Verification",
                message, true);

        return true;
    }

    /**
     * Authenticates a user based on the provided signin request.
     *
     * @param signinRequest The request containing the signin details.
     * @return The JWT authentication response if authentication is successful.
     * @throws BadCredentialsException if the provided credentials are invalid.
     * @throws DisabledException if the user account is disabled.
     * @throws UsernameNotFoundException if the username is not found.
     */
    public JwtAuthenticationResponse signin(SigninRequest signinRequest) throws
            BadCredentialsException,
            DisabledException,
            UsernameNotFoundException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),
                    signinRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        var user = userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email and password"));
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        if(user.getVerified()) {
            Optional<Shelter> optionalShelter = shelterRepository.findByUserId(user.getId());
            Optional<PetAdopter> optionalPetAdopter = petAdopterRepository.findByUserId(user.getId());
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            jwtAuthenticationResponse.setUserRole(user.getRole());
            jwtAuthenticationResponse.setUserId(user.getId());
            if(isShelterAccepted(user.getId())) {
                jwtAuthenticationResponse.setShelterId(optionalShelter.get().getId());
            }
            if(optionalPetAdopter.isPresent()) {
                jwtAuthenticationResponse.setPetAdopterId(optionalPetAdopter.get().getId());
            }
            jwtAuthenticationResponse.setVerified(user.getVerified());
            return jwtAuthenticationResponse;
        }
        jwtAuthenticationResponse.setVerified(user.getVerified());
        return jwtAuthenticationResponse;
    }

    /**
     * Checks if the shelter associated with the given user ID is accepted.
     *
     * @param userId The ID of the user.
     * @return true if the shelter is accepted, false otherwise.
     */
    public boolean isShelterAccepted (long userId) {
        Optional<Shelter> optionalShelter = shelterRepository.findByUserId(userId);
        if(optionalShelter.isPresent()) {
            Shelter shelter = optionalShelter.get();
            if(shelter.isAccepted()) {
                return true;
            } else {
                throw new RuntimeException("Admin approval pending..");
            }
        }
        return false;
    }

    /**
     * Verifies a user by email.
     *
     * @param email The email of the user to verify.
     * @return true if the user is successfully verified, false otherwise.
     */
    public boolean verifyByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setVerified(Boolean.TRUE);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    /**
     * Initiates the password reset process by sending a reset email with a password reset token.
     *
     * @param contextPath The context path of the application.
     * @param email The email of the user requesting the password reset.
     * @return A generic response indicating the status of the password reset request.
     */
    @Override
    public GenericResponse resetByEmail(final String contextPath, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        System.out.println("USER HERE IS " + optionalUser.get());
      User user = optionalUser.get();
      String token  = jwtService.generateToken(user);

      PasswordResetToken myToken = new PasswordResetToken(token, user);
      passwordTokenRepository.save(myToken);

      String url = contextPath + "/api/auth/redirectChangePassword?token=" + token;
      String linkText = "Click here to reset your password";
      String message = "<p>Password reset successfully. Please use the link below to reset your password. Note that the link is valid for " + PasswordResetToken.EXPIRATION + " minutes.</p>"
          + "<a href=\"" + url + "\">" + linkText + "</a>";

      try {
      emailService.sendEmail(user.getEmail(), "Password Reset", message, true);
    } catch (MessagingException e) {
        emailService.sendEmail(user.getEmail(), "Password Reset", message);
    }

      return new GenericResponse(
            "A password reset email has been sent. Follow the instructions inside\n" + message
      );
    }

    /**
     * Resets the user's password based on the provided password reset token.
     *
     * @param passwordDto The DTO containing the password reset details.
     * @return A generic response indicating the status of the password reset operation.
     */
    @Override
    public GenericResponse resetPassword(PasswordDto passwordDto) {
        String result = validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return new GenericResponse(result);
        }

        Optional<User> user = getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            changeUserPassword((User) user.get(), passwordDto.getNewPassword());
            invalidateResetToken(passwordDto.getToken());
            return new GenericResponse("Password reset successfully");
        } else {
            return new GenericResponse(null, "This username is invalid, or does not exist");
        }
    }

    /**
     * Validates a password reset token.
     *
     * @param token The password reset token to validate.
     * @return A string indicating the validation result ("invalidToken", "expired", or null if valid).
     */
    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
            : isTokenExpired(passToken) ? "expired"
                : null;
    }

    /**
     * Updates the user's password.
     *
     * @param passwordDto The DTO containing the password update details.
     * @return A generic response indicating the status of the password update operation.
     */
    @Override
    public GenericResponse updateUserPassword(PasswordDto passwordDto) {
        final Optional<User> user = userRepository.findByEmail(passwordDto.getEmail());
        if (user.isPresent()) {
            if (!checkIfValidOldPassword(user.get(), passwordDto.getOldPassword())) {
                return new GenericResponse(null, "Invalid Old Password.");
            }

            changeUserPassword(user.get(), passwordDto.getNewPassword());
            return new GenericResponse("Password updated successfully");
        } else {
            return new GenericResponse(null, "Not a valid user.");
        }
    }

    // ============== UTILS ============

    /**
     * Changes the user's password.
     *
     * @param user The user whose password needs to be changed.
     * @param password The new password.
     */
    private void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    /**
     * Checks if the provided old password matches the user's current password.
     *
     * @param user The user whose password is being checked.
     * @param oldPassword The old password to validate.
     * @return true if the old password is valid, false otherwise.
     */
    private boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    /**
     * Retrieves the user associated with the provided password reset token.
     *
     * @param token The password reset token.
     * @return An optional containing the user associated with the token.
     */
    private Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token) .getUser());
    }

    /**
     * Invalidates a password reset token by setting its value to null.
     *
     * @param token The password reset token to invalidate.
     */
    private void invalidateResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);
        if (passwordResetToken != null) {
            passwordResetToken.setToken(null);
            passwordTokenRepository.save(passwordResetToken);
        }
    }
}
