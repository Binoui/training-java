package com.excilys.formation.cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.model.Computer;

public interface ComputerDAO {
    Long createComputer(Computer c) throws DAOException;

    void deleteComputer(Computer c) throws DAOException;

    void deleteComputers(List<Long> idsToDelete) throws DAOException;

    void deleteComputers(long company_id, Connection conn) throws DAOException, SQLException;

    Optional<Computer> getComputer(Computer computer) throws DAOException;

    Optional<Computer> getComputer(long id) throws DAOException;

    int getComputerCount() throws DAOException;

    int getComputerCount(String searchWord) throws DAOException;

    List<Computer> getListComputers() throws DAOException;

    List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column, boolean ascending)
            throws DAOException;

    List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column, boolean ascending,
            String searchWord) throws DAOException;

    int getListComputersPageCount(int pageSize) throws DAOException;

    int getListComputersPageCount(int pageSize, String searchWord) throws DAOException;

    void updateComputer(Computer c) throws DAOException;
}
