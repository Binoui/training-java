package com.excilys.formation.cdb.services;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.validators.IncorrectValidationException;
import com.excilys.formation.cdb.validators.UnknownComputerIdException;

public interface ComputerService {

    Long createComputer(Computer c) throws IncorrectValidationException, ServiceException;

    void deleteComputer(Computer c) throws UnknownComputerIdException, ServiceException;

    void deleteComputers(List<Long> idsToDelete) throws ServiceException;

    Optional<Computer> getComputer(Computer computer) throws ServiceException;

    int getComputerCount() throws ServiceException;

    int getComputerCount(String searchWord) throws ServiceException;

    List<Computer> getComputerPage();

    List<Computer> getListComputers() throws ServiceException;

    List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column, boolean ascending)
            throws ServiceException;

    List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column, boolean ascending,
            String searchWord) throws ServiceException;

    int getListComputersPageCount(int pageSize) throws ServiceException;

    int getListComputersPageCount(int pageSize, String searchWord) throws ServiceException;

    void updateComputer(Computer c) throws IncorrectValidationException, ServiceException;

}