package com.excilys.formation.cdb.pagination;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ServiceException;

public class CompanyListPage extends Page<Company> {

    @Autowired
    private CompanyService companyService;

    public CompanyListPage() throws ServiceException {
        super();
    }

    public CompanyListPage(int pageNumber) throws ServiceException {
        super(pageNumber);
    }

    @Override
    public int getLastPageNumber() throws ServiceException {
        return companyService.getListCompaniesPageCount(pageSize);
    }

    @Override
    public void refresh() throws ServiceException {
        elements = companyService.getListCompanies(pageNumber, pageSize);
    }

}