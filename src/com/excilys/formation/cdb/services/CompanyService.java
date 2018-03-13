package com.excilys.formation.cdb.services;

import java.util.List;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.model.Company;

public class CompanyService {
	
	private CompanyDAO companyDAO = CompanyDAO.INSTANCE;

	public List<Company> getCompanyList() {
		return companyDAO.getListCompanies();
	}
}