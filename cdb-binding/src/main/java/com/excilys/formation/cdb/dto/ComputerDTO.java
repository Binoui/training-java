package com.excilys.formation.cdb.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class ComputerDTO implements Serializable {

    private static final long serialVersionUID = 7703775467384326831L;
    private long id;
    @NotNull
    @Size(min = 2, max = 40)
    private String name;
    private CompanyDTO company;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String introduced;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String discontinued;

    public CompanyDTO getCompanyDTO() {
        return company;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public long getId() {
        return id;
    }

    public String getIntroduced() {
        return introduced;
    }

    public String getName() {
        return name;
    }

    public void setCompanyDTO(CompanyDTO company) {
        this.company = company;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public void setName(String name) {
        this.name = name;
    }
}
