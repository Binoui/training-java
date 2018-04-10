package com.excilys.formation.cdb.model;

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

    private Long id;

    private String name;

    public Company() {
    }

    public Company(CompanyBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Company ").append(id).append(" : ").append(name).toString();
    }

}
