package com.excilys.formation.cdb.pagination;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ServiceException;

public class CompanyListPage extends Page<Company> {

    private CompanyService companyService;

    public CompanyListPage(CompanyService companyService) throws ServiceException {
        super();
        this.companyService = companyService;
    }

    public CompanyListPage(int pageNumber, CompanyService companyService) throws ServiceException {
        super(pageNumber);
        this.companyService = companyService;
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