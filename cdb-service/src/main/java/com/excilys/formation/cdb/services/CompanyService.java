package com.excilys.formation.cdb.services;

import java.util.List;

import com.excilys.formation.cdb.model.Company;

public interface CompanyService {

    void deleteCompany(Long id) throws ServiceException;

    List<Company> getListCompanies() throws ServiceException;

    List<Company> getListCompanies(int pageNumber, int pageSize) throws ServiceException;

    int getListCompaniesPageCount(int pageSize) throws ServiceException;

}