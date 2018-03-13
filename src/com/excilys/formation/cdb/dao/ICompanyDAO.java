package com.excilys.formation.cdb.dao;

import java.util.List;

import com.excilys.formation.cdb.model.Company;

public interface ICompanyDAO {
	public List<Company> listCompanies();
	public List<Company> listCompanies(int pageNumber, int pageSize) throws IndexOutOfBoundsException;
}
