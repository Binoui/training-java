package com.excilys.formation.cdb.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.formation.cdb.model.User;

public interface UserService {

    User loadUserByUsername(String username) throws UsernameNotFoundException;

    void addUser(User user);

}