package com.excilys.formation.cdb.dao;

import java.time.LocalDate;
import java.util.List;

import com.excilys.formation.cdb.model.Computer;

public interface IComputerDAO {
	public List<Computer> listComputers();
	public void createComputer(String name, LocalDate introduced, LocalDate discontinued, Long companyId);
	public void updateComputer(Long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId);
	public void deleteComputer(Long id);
}
