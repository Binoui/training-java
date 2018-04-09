package com.excilys.formation.cdb.services;

import java.util.List;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.dao.DAOException;
import com.excilys.formation.cdb.model.Company;

public enum CompanyService {
    INSTANCE;

    private static CompanyDAO companyDAO = CompanyDAO.INSTANCE;

    public void deleteCompany(Long id) throws ServiceException {
        try {
            companyDAO.deleteCompany(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Company> getListCompanies() throws ServiceException {
        try {
            return companyDAO.getListCompanies();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Company> getListCompanies(int pageNumber, int pageSize) throws ServiceException {
        try {
            return companyDAO.getListCompanies(pageNumber, pageSize);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public int getListCompaniesPageCount(int pageSize) throws ServiceException {
        try {
            return companyDAO.getListCompaniesPageCount(pageSize);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}