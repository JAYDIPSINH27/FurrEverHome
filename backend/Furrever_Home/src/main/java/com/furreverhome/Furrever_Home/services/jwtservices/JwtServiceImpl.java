package com.furreverhome.Furrever_Home.services.jwtservices;

import com.furreverhome.Furrever_Home.dto.auth.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.auth.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserRepository userRepository;
    private final int tokenPeriod = 1000 * 60 * 60 * 2;

    /**
     * Refreshes the JWT token.
     *
     * @param refreshTokenRequest The refresh token request containing the refresh token.
     * @return The JWT authentication response containing the new JWT token and refresh token.
     */
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        if(isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }

    /**
     * Generates a JWT token based on the UserDetails.
     *
     * @param userDetails The user details for whom the token is generated.
     * @return The JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenPeriod))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Generates a refresh token based on the UserDetails and additional claims.
     *
     * @param extraClaims  Additional claims to be included in the token.
     * @param userDetails  The user details for whom the token is generated.
     * @return The refresh token.
     */
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenPeriod))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token using the provided function.
     *
     * @param token           The JWT token from which to extract the claim.
     * @param claimsResolvers The function to resolve the desired claim from the Claims object.
     * @param <T>             The type of the claim to extract.
     * @return The extracted claim of type T.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Retrieves the signing key used for JWT token verification.
     *
     * @return The signing key.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode("e2315034876692352de9a59ef03e9f39582606f2a1acf1f1a792e9d85a77eb90");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token from which to extract claims.
     * @return The claims extracted from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    /**
     * Validates whether the JWT token is valid for the given UserDetails.
     *
     * @param token       The JWT token to be validated.
     * @param userDetails The user details against which the token is validated.
     * @return True if the token is valid for the user details, otherwise false.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token The JWT token to check for expiration.
     * @return True if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token's claims.
     *
     * @param token The JWT token from which to extract the expiration date.
     * @return The expiration date of the token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
