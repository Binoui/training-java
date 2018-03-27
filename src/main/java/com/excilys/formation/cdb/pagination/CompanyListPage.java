package com.excilys.formation.cdb.pagination;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ServiceException;

public class CompanyListPage extends Page<Company> {

    private static CompanyService service = CompanyService.INSTANCE;

    public CompanyListPage() throws ServiceException {
        super();
    }

    public CompanyListPage(int pageNumber) throws ServiceException {
        super(pageNumber);
    }

    @Override
    public int getLastPageNumber() throws ServiceException {
        return service.getListCompaniesPageCount(pageSize);
    }

    @Override
    public void refresh() throws ServiceException {
        elements = service.getListCompanies(pageNumber, pageSize);
    }

}