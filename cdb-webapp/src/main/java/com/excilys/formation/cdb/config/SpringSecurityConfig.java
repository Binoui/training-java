package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.formation.cdb.services.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    public SpringSecurityConfig(UserService userService) {
        this.userDetailsService = userService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/computer/edit", "/computer/add", "/computer/delete")
                .access("hasRole('ROLE_ADMIN')").and().formLogin().loginPage("/login").usernameParameter("username")
                .passwordParameter("password").defaultSuccessUrl("/computer/dashboard").and().logout()
                .logoutUrl("/logout").logoutSuccessUrl("/computer/dashboard").and().csrf().and().exceptionHandling()
                .accessDeniedPage("/403").and().addFilter(new TokenBasedAuthenticationFilter(authenticationManager()))
                .addFilter(new TokenBasedAuthorizationFilter(authenticationManager()));
        ;
    }
}