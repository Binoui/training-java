package com.excilys.formation.cdb.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.cdb.model.Computer;

public interface IComputerDAO {
    Long createComputer(Computer c) throws SQLException;

    void deleteComputer(Computer c);

    List<Computer> getListComputers();

    List<Computer> getListComputers(int pageNumber, int pageSize) throws IndexOutOfBoundsException;

    void updateComputer(Computer c) throws SQLException;
}
