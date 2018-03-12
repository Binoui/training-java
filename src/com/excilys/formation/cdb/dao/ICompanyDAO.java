package com.excilys.formation.cdb.dao;

import java.util.List;

import com.excilys.formation.cdb.model.Company;

public interface ICompanyDAO {
	public List<Company> listCompanies();
	public void createCompany(String name);
	public void updateCompany(Long id, String name);
	public void deleteCompany(Long id);
}
