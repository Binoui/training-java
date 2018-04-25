package com.excilys.formation.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company {
    public static class CompanyBuilder {
        private Long id;
        private String name;

        public Company build() {
            return new Company(this);
        }

        public CompanyBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CompanyBuilder withName(String name) {
            this.name = name;
            return this;
        }
    }

    @Id @GeneratedValue
    private Long ca_id;

    @Column(name="ca_name")
    private String ca_name;

    public Company() {
    }

    public Company(CompanyBuilder builder) {
        this.ca_id = builder.id;
        this.ca_name = builder.name;
    }

    public Company(Long id, String name) {
        this.ca_id = id;
        this.ca_name = name;
    }

    public Long getId() {
        return ca_id;
    }

    public String getName() {
        return ca_name;
    }

    public void setId(Long id) {
        this.ca_id = id;
    }

    public void setName(String name) {
        this.ca_name = name;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Company ").append(ca_id).append(" : ").append(ca_name).toString();
    }

}
