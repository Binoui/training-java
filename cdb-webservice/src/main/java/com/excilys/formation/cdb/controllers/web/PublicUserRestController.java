package com.excilys.formation.cdb.controllers.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.services.UserAuthenticationService;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/public/users")
public class PublicUserRestController {

    private UserAuthenticationService authentication;

    protected PublicUserRestController(UserAuthenticationService auth) {
        authentication = auth;
    }

    @PostMapping("/login")
    String login(final HttpServletRequest request, @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        return authentication.login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}
