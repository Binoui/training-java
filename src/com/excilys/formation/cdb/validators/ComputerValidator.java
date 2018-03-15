package com.excilys.formation.cdb.validators;

import java.time.LocalDate;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.dao.ComputerDAO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

public enum ComputerValidator {
	INSTANCE;
	
	private CompanyDAO companyDAO = CompanyDAO.INSTANCE;
	private ComputerDAO computerDAO = ComputerDAO.INSTANCE;
	
	public void validateComputer(Computer c) throws IncorrectValidationException {
		validateName(c.getName());
		validateDates(c.getIntroduced(), c.getDiscontinued());
		validateCompany(c.getCompany());
	}
	
	private void validateName(String name) throws NullNameException {
		if (name == null || name.isEmpty()) {
			throw new NullNameException("Computer name cannot be null.");
		}
	}
	
	private void validateDates(LocalDate introduced, LocalDate discontinued) throws InvalidDatesException {
		if (discontinued != null && introduced != null && ! introduced.isBefore(discontinued)) {
			throw new InvalidDatesException("Discontinued date cannot be before introducted date.");
		}
	}
	
	private void validateCompany(Company company) throws UnknownCompanyIdException {
		if (company != null && company.getId() != null && companyDAO.getCompany(company) == null) {
			throw new UnknownCompanyIdException("Cannot find given company.");
		}
	}

	public void validateComputerId(Long id) throws UnknownComputerIdException {
		if (id != null && computerDAO.getComputer(id) == null) {
			throw new UnknownComputerIdException("Cannot find given computer.");
		}
	}
}
