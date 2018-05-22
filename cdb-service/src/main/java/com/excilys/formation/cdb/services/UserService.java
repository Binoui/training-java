package com.excilys.formation.cdb.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.formation.cdb.model.User;

public interface UserService extends UserDetailsService {

    void addUser(User user);

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;

}