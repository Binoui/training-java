package com.excilys.formation.cdb.dao;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;

public interface IComputerDAO {
    Long createComputer(Computer c) throws DAOException;

    void deleteComputer(Computer c) throws DAOException;

    List<Computer> getListComputers() throws DAOException;

    List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column, boolean ascending) throws DAOException, IndexOutOfBoundsException;

    void updateComputer(Computer c) throws DAOException;
}
