package com.excilys.formation.cdb.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.excilys.formation.cdb.model.Computer;

public interface IComputerDAO {
	public List<Computer> listComputers();
	public List<Computer> listComputers(int pageNumber, int pageSize) throws IndexOutOfBoundsException;
	public void createComputer(Computer c) throws SQLException;
	public void updateComputer(Computer c) throws SQLException;
	public void deleteComputer(Computer c);
}
