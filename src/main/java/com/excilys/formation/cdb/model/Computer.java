package com.excilys.formation.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Computer")
public class Computer {

    public static class ComputerBuilder {

        private Long id;

        private String name;

        private Company company;

        private LocalDate introduced;

        private LocalDate discontinued;

        public ComputerBuilder() {
        }

        public Computer build() {
            return new Computer(this);
        }

        public ComputerBuilder withCompany(Company company) {
            this.company = company;
            return this;
        }

        public ComputerBuilder withDiscontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        public ComputerBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ComputerBuilder withIntroduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        public ComputerBuilder withName(String name) {
            this.name = name;
            return this;
        }
    }

    private Long id;
    private String name;
    private Company company;
    private LocalDate introduced;

    private LocalDate discontinued;

    public Computer() {
    }

    public Computer(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    public Computer(String name, LocalDate introduced, LocalDate discontinued,
            Company company) {
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ca_id")
    public Company getCompany() {
        return company;
    }

    @Column(name = "cu_discontinued")
    public LocalDate getDiscontinued() {
        return discontinued;
    }

    @Id
    @Column(name = "cu_id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    @Column(name = "cu_introduced")
    public LocalDate getIntroduced() {
        return introduced;
    }

    @Column(name = "cu_name")
    public String getName() {
        return name;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Computer ").append(id).append(" : ").append(name).append(" (")
                .append(introduced).append(" - ")
                .append(discontinued).append(") from ")
                .append(company).toString();
    }
}
