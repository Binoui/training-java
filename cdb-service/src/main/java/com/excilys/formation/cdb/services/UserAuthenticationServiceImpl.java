package com.excilys.formation.cdb.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.model.User;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthenticationService.class);

    private Map<String, User> users = new HashMap<>();
    private UserService userService;

    public UserAuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional.ofNullable(users.get(token));
    }

    @Override
    public Optional<String> login(final String username, final String password) {

        Optional<String> optionalToken;

        try {
            User user = userService.loadUserByUsername(username);
            if (BCrypt.checkpw(password, user.getPassword())) {
                String token = UUID.randomUUID().toString();
                users.put(token, user);
                optionalToken = Optional.of(token);
                LOGGER.info("user " + username + " logging in with token : " + token);
            } else {
                optionalToken = Optional.empty();
                LOGGER.info("user " + username + " entered wrong password");
            }
        } catch (UsernameNotFoundException e) {
            LOGGER.info("user not found with username " + username + " : {}", e);
            optionalToken = Optional.empty();
        }

        return optionalToken;
    }

    @Override
    public void logout(String token) {
        User userLoggingOut = users.remove(token);
        LOGGER.info("user " + userLoggingOut.getUsername() + " logged out");

    }
}
