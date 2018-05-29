package com.excilys.formation.cdb.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.formation.cdb.dao.NameAlreadyPresentException;
import com.excilys.formation.cdb.model.Role;
import com.excilys.formation.cdb.model.User;
import com.excilys.formation.cdb.model.User.UserBuilder;

@Service
@EnableTransactionManagement
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

    @Override
    @Transactional
    public Optional<String> register(String username, String password) throws NameAlreadyPresentException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(11));
        ArrayList<Role> roles = new ArrayList<>();
        Role role = new Role("ROLE_USER");
        role.setId(2L);
        roles.add(role);
        userService.addUser(new UserBuilder().withPassword(hashedPassword).withUsername(username).withRoles(roles).build());

        return login(username, password);
    }
}
