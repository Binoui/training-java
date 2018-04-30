package com.excilys.formation.cdb.model;

import java.util.Collection;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails {

    public static class UserBuilder {
        private Long id;

        private String username;

        private UserRole userRole;

        public UserBuilder() {
        }

        public User build() {
            return new User(this);
        }

        public UserBuilder withUserRole(UserRole userRole) {
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
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "us_id")
    private Long id;

    @Column(name = "us_username")
    private String username;

    private UserRole userRole;

    public User() {
    }

    public User(Long id, String username, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.userRole = userRole;
    }

    public User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.userRole = builder.userRole;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

}
