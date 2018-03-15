package com.excilys.formation.cdb.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.cdb.model.Computer;

public interface IComputerDAO {
	public List<Computer> getListComputers();
	public List<Computer> getListComputers(int pageNumber, int pageSize) throws IndexOutOfBoundsException;
	public Long createComputer(Computer c) throws SQLException;
	public void updateComputer(Computer c) throws SQLException;
	public void deleteComputer(Computer c);
}
