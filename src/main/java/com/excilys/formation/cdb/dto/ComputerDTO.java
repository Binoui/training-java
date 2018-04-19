package com.excilys.formation.cdb.dto;

import java.io.Serializable;

public class ComputerDTO implements Serializable {

    private static final long serialVersionUID = 7703775467384326831L;
    private long id;
    private String name;
    private CompanyDTO companyDTO;
    private String introduced;
    private String discontinued;

    public CompanyDTO getCompanyDTO() {
        return companyDTO;
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
        this.companyDTO = company;
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
