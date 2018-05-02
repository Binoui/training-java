package com.excilys.formation.cdb.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails {

    public static class UserBuilder {
        private Long id;

        private String username;

        private String password;

        private String userRole;

        public UserBuilder() {
        }

        public User build() {
            return new User(this);
        }

        public UserBuilder withUserRole(String userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "us_id")
    private Long id;

    @Column(name = "us_username")
    private String username;

    @Column(name = "us_password")
    private String password;

    @Column(name = "us_role")
    private String userRole;

    public User() {
    }

    public User(Long id, String username, String userRole, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.userRole = builder.userRole;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(getUserRole()));
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
