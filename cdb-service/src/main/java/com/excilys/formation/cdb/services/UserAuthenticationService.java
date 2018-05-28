package com.excilys.formation.cdb.services;

import java.util.Optional;

import com.excilys.formation.cdb.dao.NameAlreadyPresentException;
import com.excilys.formation.cdb.model.User;

public interface UserAuthenticationService {

    Optional<User> findByToken(String token);

    Optional<String> login(String username, String password);

    void logout(String token);

    Optional<String> register(String username, String password) throws NameAlreadyPresentException;

}
