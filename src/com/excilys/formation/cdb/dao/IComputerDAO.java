package com.excilys.formation.cdb.dao;

import java.time.LocalDate;
import java.util.List;

import com.excilys.formation.cdb.model.Computer;

public interface IComputerDAO {
	public List<Computer> listComputers();
	public void createComputer(Computer c);
	public void updateComputer(Computer c);
	public void deleteComputer(Computer c);
}
