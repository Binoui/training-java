package com.excilys.formation.cdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

public interface CompanyDAO {
    void createCompany(Company c);

    void deleteCompany(Long id);

    Optional<Company> getCompany(Company c);

    Optional<Company> getCompany(Long id);

    List<Computer> getCompanyComputers(int idCompany);

    int getCompanyCount(String searchWord);

    List<Company> getListCompanies();

    List<Company> getListCompanies(int pageNumber, int pageSize);

    List<Company> getListCompanies(int pageNumber, int pageSize, SortableCompanyColumn column, boolean ascending);

    List<Company> getListCompanies(int pageNumber, int pageSize, SortableCompanyColumn sortBy, boolean ascending,
            String searchWord);

    public int getListCompaniesPageCount(int pageSize);

    int getListCompaniesPageCount(int pageSize, String searchWord);

    void updateCompany(Company c);

}
