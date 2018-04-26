package com.excilys.formation.cdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.model.Company;

public interface CompanyDAO {
    void deleteCompany(long id);

    Optional<Company> getCompany(Company c);

    Optional<Company> getCompany(Long id);

    List<Company> getListCompanies();

    List<Company> getListCompanies(int pageNumber, int pageSize);

    public int getListCompaniesPageCount(int pageSize);
}
