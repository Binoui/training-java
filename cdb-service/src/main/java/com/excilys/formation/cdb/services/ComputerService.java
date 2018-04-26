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

    void deleteComputers(List<Long> idsToDelete);

    Optional<Computer> getComputer(Computer computer);

    int getComputerCount();

    int getComputerCount(String searchWord);

    List<Computer> getComputerPage();

    List<Computer> getListComputers();

    List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column, boolean ascending)
           ;

    List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column, boolean ascending,
            String searchWord);

    int getListComputersPageCount(int pageSize);

    int getListComputersPageCount(int pageSize, String searchWord);

    void updateComputer(Computer c) throws IncorrectValidationException, ServiceException;

}