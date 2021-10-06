package com.sample.enterpriseapp.securities;

import com.sample.enterpriseapp.models.Roles;
import com.sample.enterpriseapp.repositories.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Set;

@Component
public class JwtTokenProvider {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppUserDetails appUserDetails;


    public String createToken(String username, Set<Roles> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", roles);

        Date validityPeriod = new Date(new Date().getTime() + 1500000);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validityPeriod)
                .signWith(SignatureAlgorithm.HS256, "pass1223")
                .compact();
    }

    public Authentication getAuthentication(String token) {
        try {
            UserDetails userDetails = appUserDetails.loadUserByUsername(getUsername(token));
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey("pass1223").parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey("pass1223").parseClaimsJws(token);
            String username = getUsername(token);
            return !userRepository.findByUsername(username).get().getToken().isEmpty();
        } catch (JwtException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User session has ended, try logging in");
        }
    }
}

