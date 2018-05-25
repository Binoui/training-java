package com.excilys.formation.cdb.config;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/company/**", HttpMethod.POST.toString()),
            new AntPathRequestMatcher("/company/**", HttpMethod.PUT.toString()),
            new AntPathRequestMatcher("/company/**", HttpMethod.DELETE.toString()),
            new AntPathRequestMatcher("/computer/**", HttpMethod.POST.toString()),
            new AntPathRequestMatcher("/computer/**", HttpMethod.PUT.toString()),
            new AntPathRequestMatcher("/computer/**", HttpMethod.DELETE.toString()),
            new AntPathRequestMatcher("/computer/**", HttpMethod.DELETE.toString()),
            new AntPathRequestMatcher("/logout/**"),
            new AntPathRequestMatcher("/current/**", HttpMethod.GET.toString()));

    private static final RequestMatcher PUBLIC_URLS = new NegatedRequestMatcher(PROTECTED_URLS);

    private final TokenAuthenticationProvider provider;

    public SecurityConfig(final TokenAuthenticationProvider provider) {
        super();
        this.provider = requireNonNull(provider);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(STATELESS).and().exceptionHandling()
                .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS).and()
                .authenticationProvider(provider)
                .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class).authorizeRequests()
                .antMatchers(HttpMethod.PUT).access("hasRole('ROLE_ADMIN')").antMatchers(HttpMethod.DELETE)
                .access("hasRole('ROLE_ADMIN')").anyRequest().authenticated().and().csrf().disable().formLogin()
                .disable().httpBasic().disable().logout().disable();
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().requestMatchers(PUBLIC_URLS);
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(UNAUTHORIZED);
    }

    @Bean
    TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
        final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(PROTECTED_URLS);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(successHandler());
        return filter;
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(new NoRedirectStrategy());
        return successHandler;
    }

}