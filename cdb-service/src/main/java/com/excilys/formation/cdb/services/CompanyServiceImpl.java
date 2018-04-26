package com.excilys.formation.cdb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.model.Company;

@Service("CompanyService")
@EnableTransactionManagement
public class CompanyServiceImpl implements CompanyService {

    private CompanyDAO companyDAO;

    public CompanyServiceImpl(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteCompany(Long id) throws ServiceException {
        companyDAO.deleteCompany(id);
    }

    @Override
    public List<Company> getListCompanies() throws ServiceException {
        return companyDAO.getListCompanies();
    }

    @Override
    public List<Company> getListCompanies(int pageNumber, int pageSize) throws ServiceException {
        return companyDAO.getListCompanies(pageNumber, pageSize);
    }

    @Override
    public int getListCompaniesPageCount(int pageSize) throws ServiceException {
        return companyDAO.getListCompaniesPageCount(pageSize);
    }
}