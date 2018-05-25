package com.excilys.formation.cdb.controllers.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.services.UserAuthenticationService;

@RestController
@CrossOrigin
public class PublicUserRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerRestControllerImpl.class);
    private UserAuthenticationService authentication;

    protected PublicUserRestController(UserAuthenticationService auth) {
        authentication = auth;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(final HttpServletRequest request,
            @RequestParam("username") final String username, @RequestParam("password") final String password) {
        Optional<String> token = authentication.login(username, password);

        if (token.isPresent()) {
            return new ResponseEntity<>(token.get(), HttpStatus.OK);
        } else {
            LOGGER.info("User tried to log in with wrong infos. User : " + username + ", password : " + password);
            return new ResponseEntity<>("wrong username or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
