package com.excilys.formation.cdb.services;

import java.util.Optional;

import com.excilys.formation.cdb.model.User;

public interface UserAuthenticationService {

    Optional<String> login(String username, String password);

    Optional<User> findByToken(String token);

    void logout(User user);

}
