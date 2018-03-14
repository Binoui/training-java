package com.excilys.formation.cdb.services;

import java.time.LocalDate;
import java.util.List;

import com.excilys.formation.cdb.dao.ComputerDAO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.validators.ComputerValidator;
import com.excilys.formation.cdb.validators.IncorrectValidationException;

public enum ComputerService {
	INSTANCE;
	
	private ComputerDAO dao = ComputerDAO.INSTANCE;
	private ComputerValidator validator = ComputerValidator.INSTANCE;

	public List<Computer> getListComputers() {
		return dao.getListComputers();
	}
	
	public List<Computer> getPageComputers(int page, int pageSize) {
		List<Computer> computers = null;
		
		try {
			computers = dao.getListComputers(page, pageSize);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		
		return computers;
	}
	
	public void createComputer(String name, LocalDate introduced, LocalDate discontinued, Company company) throws IncorrectValidationException {
		Computer c = new Computer(name, introduced, discontinued, company);
		validator.validateComputer(c);
		dao.createComputer(c);
	}
}
