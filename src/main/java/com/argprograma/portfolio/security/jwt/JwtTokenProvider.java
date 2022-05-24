package com.argprograma.portfolio.security.jwt;

import com.argprograma.portfolio.exceptions.PortfolioAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
//    @Value("${app.jwt-secret}")
    private String jwtSecret = System.getenv("arg-prog-jwt-secret");
    @Value("${app.jwt-expiration-milliseconds}")
    private Integer jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date actualDate =  new Date();
        Date expirationDate = new Date(actualDate.getTime() + jwtExpirationInMs);

        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }
        catch (SignatureException e) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "Invalid JWT");
        }
        catch (MalformedJwtException e) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "Invalid JWT");
        }
        catch (ExpiredJwtException e) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "Expired JWT");
        }
        catch (PortfolioAppException e) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "Not compatible JWT");
        }
        catch (IllegalArgumentException e) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "Empty JWT claims");
        }
    }

}