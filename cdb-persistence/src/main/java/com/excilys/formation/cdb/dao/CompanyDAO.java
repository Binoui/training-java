package com.excilys.formation.cdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

public interface CompanyDAO {
    void deleteCompany(Long id);

    Optional<Company> getCompany(Company c);

    Optional<Company> getCompany(Long id);

    List<Computer> getCompanyComputers(int idCompany);

    List<Company> getListCompanies();

    List<Company> getListCompanies(int pageNumber, int pageSize);

    public int getListCompaniesPageCount(int pageSize);

    void updateCompany(Company c);

    List<Company> getListCompanies(int pageNumber, int pageSize, SortableCompanyColumn sortBy, boolean ascending,
            String searchWord);

    void createCompany(Company c);
}
