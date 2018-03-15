package com.excilys.formation.cdb.services;

import java.util.List;

import com.excilys.formation.cdb.dao.ComputerDAO;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.validators.ComputerValidator;
import com.excilys.formation.cdb.validators.IncorrectValidationException;
import com.excilys.formation.cdb.validators.UnknownComputerIdException;

public enum ComputerService {
	INSTANCE;
	
	private ComputerDAO dao = ComputerDAO.INSTANCE;
	private ComputerValidator validator = ComputerValidator.INSTANCE;

	public List<Computer> getListComputers() {
		return dao.getListComputers();
	}
	
	public List<Computer> getListComputers(int pageNumber, int pageSize) {
		return dao.getListComputers(pageNumber, pageSize);
	}
	
	public int getListComputersPageCount(int pageSize) {
		return dao.getListComputersPageCount(pageSize);
	}
	
	public Computer getComputer(Long id) {
		return dao.getComputer(id);
	}
	
	public void createComputer(Computer c) throws IncorrectValidationException {
		validator.validateComputer(c);
		dao.createComputer(c);
	}
	
	public void updateComputer(Computer c) throws IncorrectValidationException {
		validator.validateComputerId(c.getId());
		validator.validateComputer(c);
		dao.updateComputer(c);
	}

	public void deleteComputer(Long id) throws UnknownComputerIdException {
		validator.validateComputerId(id);
		Computer c = new Computer();
		c.setId(id);
		dao.deleteComputer(c);
	}
}
