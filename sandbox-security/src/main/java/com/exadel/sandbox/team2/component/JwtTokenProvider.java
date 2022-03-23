package com.exadel.sandbox.team2.component;

import com.exadel.sandbox.team2.dto.TokenDto;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${authorization.secret}")
    private String secret;

    @Value("${authorization.expiration}")
    private int expiration;

    public TokenDto generateJwtToken(String username, String refreshToken) {
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration* 1000L))
                .signWith(SignatureAlgorithm.HS512, secret)
                .setId(refreshToken)
                .compact();
        return new TokenDto(accessToken,refreshToken);
    }

    public int validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return 200;
        } catch (SignatureException e) {
            return 500;
        } catch (MalformedJwtException e) {
            return 501;
        } catch (ExpiredJwtException e) {
            return 201;
        } catch (UnsupportedJwtException e) {
            return 502;
        } catch (IllegalArgumentException e) {
            return 503;
        }
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
