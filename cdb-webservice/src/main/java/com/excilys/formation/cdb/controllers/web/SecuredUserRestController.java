package com.excilys.formation.cdb.controllers.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public User getCurrent(@AuthenticationPrincipal final User user) {
        return user;
    }

    @GetMapping("/logout")
    public boolean logout(@AuthenticationPrincipal final User user) {
        authentication.logout(user);
        return true;
    }
}
