/**
 * @author: 김세영
 * @version: 0.1
 * @param: Auth와 Refresh Token 발급담당
 * @see: io.jsonwebtoken
 */



package com.scarf.fracas.authsvr.Utils;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import com.scarf.fracas.authsvr.Service.ScarfRUserDetails;


import io.jsonwebtoken.*;

@Component
public class JwtUtils implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static final long serialVersionUID = -25591751598348234L;

    @Value("${jwt.secret}")
    private String jwtsecret;

    @Value("${jwt.ExpirationMs}")
    private Long jwtExpirationMs;

    public String generateAuthToken(Authentication authentication) {

        ScarfRUserDetails scarfRUserPrincipal = (ScarfRUserDetails) authentication.getPrincipal();

        return Jwts.builder()
        .setSubject((scarfRUserPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtsecret)
        .compact();
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtsecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtsecret).compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtsecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT Signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
    
}
