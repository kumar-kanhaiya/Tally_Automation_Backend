package com.tallybackend.tally_backend.security;

import com.tallybackend.tally_backend.entity.Type.ProviderType;
import com.tallybackend.tally_backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.core.user.OAuth2User;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        long TWO_MONTHS = 1000L * 60 * 60 * 24 * 60;

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getUserId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TWO_MONTHS))
                .signWith(getSecretKey())
                .compact();
    }


    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public String determineUsernameFromOAuth2User(OAuth2User oAuth2User ,String registrationId
            ,String providerId){
        String email = oAuth2User.getAttribute("email");
        if(email != null || !email.isBlank()){
            return email;
        }

        return switch (registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            default -> providerId;
        };
    }

    public String determineProviderIdFromOAuth2User(OAuth2User oAuth2User , String registrationId){
        String providerId = switch (registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            default -> {
                log.error("Unsupported OAuth2 Provider {}",registrationId);
                throw new IllegalArgumentException("Unsupported OAuth2 Provider " + registrationId);
            }
        };

        if(providerId == null || !providerId.isBlank()){
            log.error("Unable to determine Provider Id with Provider: {}",registrationId);
            throw new IllegalArgumentException("Unable to determine providerId for OAuth2 login");
        }
        return providerId;
    }

    public ProviderType getProviderTypeFromRegistrationId(String registrationId){
        return switch (registrationId.toLowerCase()){
            case "google" -> ProviderType.GOOGLE;
            default -> throw new IllegalArgumentException("Unsupported OAuth2 Provider "  + registrationId);
        };
    }
}
