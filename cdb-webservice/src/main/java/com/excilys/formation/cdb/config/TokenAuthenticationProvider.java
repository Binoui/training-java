package com.excilys.formation.cdb.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.services.UserAuthenticationService;

import java.util.Optional;


@Component
public final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    
  private UserAuthenticationService auth;
  
  protected TokenAuthenticationProvider(UserAuthenticationService auth) {
      this.auth = auth;
  }

  @Override
  protected void additionalAuthenticationChecks(final UserDetails d, final UsernamePasswordAuthenticationToken auth) {
  }

  @Override
  protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) {
    final Object token = authentication.getCredentials();
    return Optional
      .ofNullable(token)
      .map(String::valueOf)
      .flatMap(auth::findByToken)
      .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
  }
}
