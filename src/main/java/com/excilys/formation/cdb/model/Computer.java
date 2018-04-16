package com.excilys.formation.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

public class Computer {

    public static class ComputerBuilder {
        private Long id;

        private String name;

        private Optional<Company> company;

        private Optional<LocalDate> introduced;

        private Optional<LocalDate> discontinued;
        
        public ComputerBuilder() {
            introduced = Optional.empty();
            discontinued = Optional.empty();
            company = Optional.empty();
        }

        public Computer build() {
            return new Computer(this);
        }

        public ComputerBuilder withCompany(Company company) {
            this.company = Optional.ofNullable(company);
            return this;
        }

        public ComputerBuilder withDiscontinued(LocalDate discontinued) {
            this.discontinued = Optional.ofNullable(discontinued);
            return this;
        }

        public ComputerBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ComputerBuilder withIntroduced(LocalDate introduced) {
            this.introduced = Optional.ofNullable(introduced);
            return this;
        }

        public ComputerBuilder withName(String name) {
            this.name = name;
            return this;
        }
    }

    private Long id;
    private String name;
    private Optional<Company> company;
    private Optional<LocalDate> introduced;

    private Optional<LocalDate> discontinued;

    public Computer() {
    }

    public Computer(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    public Computer(String name, Optional<LocalDate> introduced, Optional<LocalDate> discontinued, Optional<Company> company) {
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    public Optional<Company> getCompany() {
        return company;
    }

    public Optional<LocalDate> getDiscontinued() {
        return discontinued;
    }

    public Long getId() {
        return id;
    }

    public Optional<LocalDate> getIntroduced() {
        return introduced;
    }

    public String getName() {
        return name;
    }

    public void setCompany(Company company) {
        this.company = Optional.ofNullable(company);
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = Optional.ofNullable(discontinued);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = Optional.ofNullable(introduced);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Computer ").append(id).append(" : ").append(name).append(" (")
                .append(introduced).append(" - ").append(discontinued).append(") from ").append(company).toString();
    }
}
