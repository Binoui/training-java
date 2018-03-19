package com.excilys.formation.cdb.dao;

import java.util.List;

import com.excilys.formation.cdb.model.Company;

public interface ICompanyDAO {
    List<Company> getListCompanies();

    List<Company> getListCompanies(int pageNumber, int pageSize) throws IndexOutOfBoundsException;
}
