package com.excilys.formation.cdb.dao;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;

public interface ICompanyDAO {
	public List<Computer> listCompanies();
	public void createCompany(String name);
	public void updateCompany(Long id, String name);
	public void deleteCompany(Long id);
}
