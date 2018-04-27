package com.excilys.formation.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class User {

    public static class UserBuilder {
        private Long id;

        private String name;

        private Company company;

        public UserBuilder() {
        }

        public User build() {
            return new User(this);
        }

        public UserBuilder withCompany(Company company) {
            this.company = company;
            return this;
        }

        public UserBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ur_id")
    private Long id;

    @Column(name = "us_username")
    private String username;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ur_id")
    private UserRole userRole;

    public User() {
    }

    public User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.name;
    }

    public User(String name, String username, UserRole userRole) {
        this.username = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return username;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.username = name;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("User ").append(id).append(" : ").append(username).append(" (")
                .append(introduced).append(" - ").append(discontinued).append(") from ").append(company).toString();
    }
}
