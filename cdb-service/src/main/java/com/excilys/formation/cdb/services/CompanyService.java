package com.excilys.formation.cdb.services;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.dao.SortableCompanyColumn;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

public interface CompanyService {

    void createCompany(Company c);

    void deleteCompany(Long id);

    int getCompaniesCount(String searchWord);

    Optional<Company> getCompany(Company c);

    Optional<Company> getCompany(Long l);

    List<Computer> getCompanyComputers(int idCompany);

    List<Company> getListCompanies();

    List<Company> getListCompanies(int pageNumber, int pageSize, SortableCompanyColumn column, boolean ascending,
            String search);

    int getListCompaniesPageCount(int pageSize);

    int getListCompaniesPageCount(int size, String search);

    void updateCompany(Company c);
}