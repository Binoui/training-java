package com.excilys.formation.cdb.pagination;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.services.CompanyService;

public class CompanyListPage extends Page<Company> {
	
	private CompanyService service = CompanyService.INSTANCE;
	
	public CompanyListPage(int pageNumber) {
		super(pageNumber);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLastPageNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
