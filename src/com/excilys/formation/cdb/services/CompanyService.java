package com.excilys.formation.cdb.services;

import java.util.List;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.pagination.CompanyListPage;
import com.excilys.formation.cdb.pagination.Page;

public enum CompanyService {
	INSTANCE;
	
	private CompanyDAO companyDAO = CompanyDAO.INSTANCE;

	public List<Company> getListCompanies() {
		return companyDAO.getListCompanies();
	}
	
	public List<Company> getListCompanies(int pageNumber, int pageSize) {
		return companyDAO.getListCompanies(pageNumber, pageSize);
	}
	
//	public List<Company> getListCompaniesPage(int pageNumber) {
//		Page p = new CompanyListPage(pageNumber);
//		
//	}
	
	public int getListCompaniesPageCount(int pageSize) {
		return companyDAO.getListCompaniesPageCount(pageSize);
	}
}