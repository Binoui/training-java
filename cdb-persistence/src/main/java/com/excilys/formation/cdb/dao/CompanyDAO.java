package com.excilys.formation.cdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.model.Company;

public interface CompanyDAO {
    void deleteCompany(long id) throws DAOException;

    Optional<Company> getCompany(Company c) throws DAOException;

    Optional<Company> getCompany(Long id) throws DAOException;

    List<Company> getListCompanies() throws DAOException;

    List<Company> getListCompanies(int pageNumber, int pageSize) throws DAOException;

    public int getListCompaniesPageCount(int pageSize) throws DAOException;
}
