package com.excilys.formation.cdb.validators;

import java.time.LocalDate;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

public enum ComputerValidator {
	INSTANCE;
	
	private CompanyDAO dao = CompanyDAO.INSTANCE;
	
	public void validateComputer(Computer c) throws IncorrectValidationException {
		validateName(c.getName());
		validateDates(c.getIntroduced(), c.getDiscontinued());
		validateCompany(c.getCompany());
	}
	
	private void validateName(String name) throws NullNameException {
		if (name == null) {
			throw new NullNameException("Computer Name cannot be null");
		}
	}
	
	private void validateDates(LocalDate introduced, LocalDate discontinued) throws InvalidDatesException {
		if (! introduced.isBefore(discontinued)) {
			throw new InvalidDatesException("Discontinued date cannot be before introducted date");
		}
	}
	
	private void validateCompany(Company company) throws UnknownCompanyIdException {
		if (company != null && dao.getCompany(company) == null) {
			throw new UnknownCompanyIdException("Cannot find given company");
		}
	}
	
}
