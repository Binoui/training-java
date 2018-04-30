package com.excilys.formation.cdb.dao;

import java.util.Optional;

import com.excilys.formation.cdb.model.User;

public interface UserDAO {

    Optional<User> getUserByUsername(String username);
    void addUser(User user);

}