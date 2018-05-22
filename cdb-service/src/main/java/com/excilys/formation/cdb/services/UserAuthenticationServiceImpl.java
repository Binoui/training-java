package com.excilys.formation.cdb.services;

import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.model.User;
import com.excilys.formation.cdb.model.User.UserBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    Map<String, User> users = new HashMap<>();

    @Override
    public Optional<String> login(final String username, final String password) {
        final String token = UUID.randomUUID().toString();
        final User user = new UserBuilder().withToken(token).withUsername(username).withPassword(password).build();

        users.put(token, user);
        return Optional.of(token);
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional.ofNullable(users.get(token));
    }

    @Override
    public void logout(final User user) {
        users.remove(user.getToken());
    }
}
