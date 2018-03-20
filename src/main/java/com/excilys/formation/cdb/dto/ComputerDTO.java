package com.excilys.formation.cdb.dto;

import java.io.Serializable;

public class ComputerDTO implements Serializable {

    private static final long serialVersionUID = 7703775467384326831L;
    private long id;
    private String name;
    private String companyName;
    private String introduced;
    private String discontinued;

    public ComputerDTO() {
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
