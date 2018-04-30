package com.excilys.formation.cdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build())
                .withUser(User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN").build());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/computer/edit", "/computer/add", "/computer/delete")
                .access("hasRole('ROLE_ADMIN')").and().formLogin().loginPage("/login").usernameParameter("username")
                .passwordParameter("password").and().logout().logoutSuccessUrl("/computer/dashboard").and().csrf().and()
                .exceptionHandling().accessDeniedPage("/403");
    }
}