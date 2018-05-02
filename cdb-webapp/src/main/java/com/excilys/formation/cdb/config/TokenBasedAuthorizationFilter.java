package com.excilys.formation.cdb.config;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class TokenBasedAuthorizationFilter extends BasicAuthenticationFilter {

    TokenBasedAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
 
        String authorizationToken = request.getHeader(HttpHeaders.AUTHORIZATION);
 
        if (authorizationToken != null && authorizationToken.startsWith(JjwtConfig.TOKEN_PREFIX)) {
            authorizationToken = authorizationToken.replaceFirst(JjwtConfig.TOKEN_PREFIX, "");
 
            String username = Jwts.parser()
                    .setSigningKey(JjwtConfig.TOKEN_SECRET)
                    .parseClaimsJws(authorizationToken)
                    .getBody()
                    .getSubject();
 
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()));
        }
 
        chain.doFilter(request, response);
    }

}