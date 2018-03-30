package com.excilys.formation.cdb.dao;

import java.util.List;

import com.excilys.formation.cdb.model.Company;

public interface ICompanyDAO {
    void deleteCompany(long id) throws DAOException;

    List<Company> getListCompanies() throws DAOException;

    List<Company> getListCompanies(int pageNumber, int pageSize) throws DAOException, IndexOutOfBoundsException;
}
