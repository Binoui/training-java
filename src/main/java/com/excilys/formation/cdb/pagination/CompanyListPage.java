package com.excilys.formation.cdb.pagination;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.services.CompanyService;

public class CompanyListPage extends Page<Company> {

    private static CompanyService service = CompanyService.INSTANCE;

    public CompanyListPage() {
        super();
    }

    public CompanyListPage(int pageNumber) {
        super(pageNumber);
    }

    @Override
    public int getLastPageNumber() {
        return service.getListCompaniesPageCount(pageSize);
    }

    @Override
    public void refresh() {
        elements = service.getListCompanies(pageNumber, pageSize);
    }

}
