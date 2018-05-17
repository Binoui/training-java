package com.excilys.formation.cdb.services;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

public interface CompanyService {

    void deleteCompany(Long id);

    Optional<Company> getCompany(Long l);

    List<Company> getListCompanies();

    List<Company> getListCompanies(int pageNumber, int pageSize);

    int getListCompaniesPageCount(int pageSize);

    Optional<Company> getCompany(Company c);

    void updateCompany(Company c);
}