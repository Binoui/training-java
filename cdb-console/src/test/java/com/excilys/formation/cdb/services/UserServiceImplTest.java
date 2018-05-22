package com.excilys.formation.cdb.services;

import static org.junit.Assert.assertTrue;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.excilys.formation.cdb.config.ConsoleConfig;
import com.excilys.formation.cdb.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConsoleConfig.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("cli")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void test() throws UserPrincipalNotFoundException {
        User user = userService.loadUserByUsername("user");
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        User admin = userService.loadUserByUsername("admin");
        assertTrue(admin.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

}
