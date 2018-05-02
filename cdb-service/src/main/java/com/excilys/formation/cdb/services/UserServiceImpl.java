package com.excilys.formation.cdb.services;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.dao.UserDAO;
import com.excilys.formation.cdb.model.User;

@Service
public class UserServiceImpl implements UserService {

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
    
    private UserDAO userDAO;
    
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public final User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        detailsChecker.check(user);
        return user;
    }

    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }
}