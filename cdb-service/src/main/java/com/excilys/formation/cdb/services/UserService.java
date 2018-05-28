package com.excilys.formation.cdb.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.formation.cdb.dao.NameAlreadyPresentException;
import com.excilys.formation.cdb.model.User;

public interface UserService extends UserDetailsService {

    void addUser(User user) throws NameAlreadyPresentException;

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;

}