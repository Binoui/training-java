package com.excilys.formation.cdb.dto;

import java.io.Serializable;

public class CompanyDTO implements Serializable {

    private static final long serialVersionUID = 3748255250522905424L;
    private long id;
    private String name;

    public CompanyDTO() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
