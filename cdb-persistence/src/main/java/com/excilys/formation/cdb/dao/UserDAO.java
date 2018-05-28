package com.excilys.formation.cdb.dao;

import java.util.Optional;

import com.excilys.formation.cdb.model.User;

public interface UserDAO {

    void addUser(User user) throws NameAlreadyPresentException;

    Optional<User> getUserByUsername(String username);

}