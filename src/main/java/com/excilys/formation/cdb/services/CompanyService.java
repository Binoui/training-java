package com.excilys.formation.cdb.services;

import java.util.List;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.model.Company;

public enum CompanyService {
    INSTANCE;

    private static CompanyDAO companyDAO = CompanyDAO.INSTANCE;

    public List<Company> getListCompanies() {
        return companyDAO.getListCompanies();
    }

    public List<Company> getListCompanies(int pageNumber, int pageSize) {
        return companyDAO.getListCompanies(pageNumber, pageSize);
    }

    public int getListCompaniesPageCount(int pageSize) {
        return companyDAO.getListCompaniesPageCount(pageSize);
    }
}