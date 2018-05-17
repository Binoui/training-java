package com.excilys.formation.cdb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

@Service("CompanyService")
@EnableTransactionManagement
public class CompanyServiceImpl implements CompanyService {

    private CompanyDAO companyDAO;

    public CompanyServiceImpl(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Override
    @Transactional
    public void deleteCompany(Long id) {
        companyDAO.deleteCompany(id);
    }
    
    @Override
    @Transactional
    public void updateCompany(Company c) {
        companyDAO.updateCompany(c);
    }

    @Override
    public Optional<Company> getCompany(Company c) {
        return companyDAO.getCompany(c);
    }

    @Override
    public Optional<Company> getCompany(Long id) {
        return companyDAO.getCompany(id);
    }

    @Override
    public List<Company> getListCompanies() {
        return companyDAO.getListCompanies();
    }

    @Override
    public List<Company> getListCompanies(int pageNumber, int pageSize) {
        return companyDAO.getListCompanies(pageNumber, pageSize);
    }

    @Override
    public int getListCompaniesPageCount(int pageSize) {
        return companyDAO.getListCompaniesPageCount(pageSize);
    }
}