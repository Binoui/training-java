package com.excilys.formation.cdb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.dao.SortableCompanyColumn;
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
    public Optional<Company> getCompany(Company c) {
        return companyDAO.getCompany(c);
    }

    @Override
    public Optional<Company> getCompany(Long id) {
        return companyDAO.getCompany(id);
    }

    @Override
    public List<Computer> getCompanyComputers(int idCompany) {
        return companyDAO.getCompanyComputers(idCompany);
    }

    @Override
    public List<Company> getListCompanies() {
        return companyDAO.getListCompanies();
    }

    @Override
    public int getListCompaniesPageCount(int pageSize) {
        return companyDAO.getListCompaniesPageCount(pageSize);
    }

    @Override
    @Transactional
    public void updateCompany(Company c) {
        companyDAO.updateCompany(c);
    }

    @Override
    public List<Company> getListCompanies(int pageNumber, int pageSize, SortableCompanyColumn column, boolean ascending,
            String search) {
        return companyDAO.getListCompanies(pageNumber, pageSize, column, ascending, search);
    }

    @Override
    public void createCompany(Company c) {
        companyDAO.createCompany(c);
    }
}