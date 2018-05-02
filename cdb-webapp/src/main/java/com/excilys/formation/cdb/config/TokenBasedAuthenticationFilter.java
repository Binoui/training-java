package com.excilys.formation.cdb.config;

import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenBasedAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    TokenBasedAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws IOException, ServletException {

        String token = Jwts.builder().setId(UUID.randomUUID().toString())
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + JjwtConfig.TOKEN_LIFETIME))
                .signWith(SignatureAlgorithm.HS512, JjwtConfig.TOKEN_SECRET).compact();

        response.addHeader(HttpHeaders.AUTHORIZATION, JjwtConfig.TOKEN_PREFIX + token);

    }
}