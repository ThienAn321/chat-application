package com.example.backendchat.security.jwt;

import com.example.backendchat.constant.ErrorCodeConstant;
import com.example.backendchat.security.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for creating and validating JWT tokens.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.token-expiration}")
    private long jwtExpiration;

    @Value("${security.jwt.refresh-token-expiration}")
    private long refreshExpirationDate;
    private final UserDetailsService customUserDetailsService;

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token The JWT token
     * @return The username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token The JWT token
     * @param claimsResolver Function to resolve the claim
     * @param <T> Type of the claim
     * @return The claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for a user.
     *
     * @param userDetails User details
     * @param isRefresh True if generating a refresh token, false for access token
     * @return The generated JWT token
     */
    public String generateToken(CustomUserDetails userDetails, boolean isRefresh) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getId());
        if (isRefresh) {
            claims.put("isRefresh", true);
        }
        return generateToken(claims, userDetails, isRefresh);
    }

    /**
     * Generates a JWT token with additional claims.
     *
     * @param extraClaims Additional claims
     * @param userDetails User details
     * @param isRefresh True if generating a refresh token, false for access token
     * @return The generated JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, boolean isRefresh) {
        long expirationDate = isRefresh ? refreshExpirationDate : jwtExpiration;
        return buildToken(extraClaims, userDetails, expirationDate);
    }

    /**
     * Builds a JWT token with specified claims and expiration.
     *
     * @param extraClaims Additional claims
     * @param userDetails User details
     * @param expiration Token expiration time in milliseconds
     * @return The JWT token
     */
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks if the JWT token is valid.
     *
     * @param token The JWT token
     * @param userDetails User details
     * @return True if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token The JWT token
     * @return True if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token
     * @return The expiration date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token
     * @return The claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token is a refresh token.
     *
     * @param token The JWT token
     * @return True if the token is a refresh token, false otherwise
     */
    public boolean isRefreshToken(String token) {
        return extractClaim(token, claims -> claims.get("isRefresh", Boolean.class)) != null;
    }

    /**
     * Generates an access token from a refresh token.
     *
     * @param refreshToken The refresh token
     * @return The generated access token
     * @throws IllegalArgumentException if the refresh token is invalid
     */
    public String generateAccessTokenFromRefreshToken(String refreshToken) {
        final String username = extractUsername(refreshToken);
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
        if (isRefreshToken(refreshToken) && !isTokenExpired(refreshToken)) {
            return generateToken(userDetails, false);
        }
        throw new IllegalArgumentException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.INVALID_REFRESH_TOKEN));
    }

    /**
     * Retrieves the signing key for JWT token.
     *
     * @return The signing key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
