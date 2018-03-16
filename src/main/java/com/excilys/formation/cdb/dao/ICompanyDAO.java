package com.excilys.formation.cdb.dao;

import java.util.List;

import com.excilys.formation.cdb.model.Company;

public interface ICompanyDAO {
	public List<Company> getListCompanies();
	public List<Company> getListCompanies(int pageNumber, int pageSize) throws IndexOutOfBoundsException;
}
