package com.excilys.formation.cdb.controllers.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.model.User;
import com.excilys.formation.cdb.services.UserAuthenticationService;

@RestController
@CrossOrigin
public class SecuredUserRestController {

    private UserAuthenticationService authentication;

    protected SecuredUserRestController(UserAuthenticationService auth) {
        authentication = auth;
    }

    @GetMapping("/current")
    public User getCurrent(@RequestParam String token) {
        return authentication.findByToken(token).orElse(null);
    }

    @GetMapping("/logout")
    public boolean logout(@RequestParam String token) {
        authentication.logout(token);
        return true;
    }
}
