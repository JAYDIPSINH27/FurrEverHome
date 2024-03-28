package com.furreverhome.Furrever_Home.services.jwtservices;

import com.furreverhome.Furrever_Home.dto.auth.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.auth.RefreshTokenRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    String extractUserName(String token);

    boolean isTokenValid(String jwt, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}