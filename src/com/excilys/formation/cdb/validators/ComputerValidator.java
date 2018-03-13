package com.excilys.formation.cdb.validators;

import java.time.LocalDate;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.model.Computer;

public enum ComputerValidator {
	INSTANCE;
	
	private CompanyDAO dao = CompanyDAO.INSTANCE;
	
	public void validateComputer(Computer c) throws IncorrectValidationException {
		validateName(c.getName());
		validateDates(c.getIntroduced(), c.getDiscontinued());
		validateCompanyId(c.getCompanyId());
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
	
	private void validateCompanyId(Long companyId) throws UnknownCompanyIdException {
		if (companyId != null && dao.getCompany(companyId) == null) {
			throw new UnknownCompanyIdException("Cannot find given company id");
		}
	}
	
}
