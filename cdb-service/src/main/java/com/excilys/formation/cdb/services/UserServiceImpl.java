package com.excilys.formation.cdb.services;

import javax.transaction.Transactional;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.formation.cdb.dao.NameAlreadyPresentException;
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
    public void addUser(User user) throws NameAlreadyPresentException {
        userDAO.addUser(user);
    }

    @Override
    public final User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        detailsChecker.check(user);
        return user;
    }
}