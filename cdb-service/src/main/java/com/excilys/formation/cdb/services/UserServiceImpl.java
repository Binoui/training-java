package com.excilys.formation.cdb.services;

import java.util.Optional;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.dao.UserDAO;
import com.excilys.formation.cdb.model.User;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
    
    private UserDAO userDAO;
    
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public final User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userDAO.getUserByUsername(username);
        
        if (! optionalUser.isPresent()) {
            throw new UsernameNotFoundException("user not found");
        }
        User user = optionalUser.get();
        detailsChecker.check(user);
        return user;
    }

    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }
}