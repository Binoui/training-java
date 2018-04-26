package com.excilys.formation.cdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.model.Computer;

public interface ComputerDAO {
    Long createComputer(Computer c);

    void deleteComputer(Computer c);

    void deleteComputers(List<Long> idsToDelete);

    Optional<Computer> getComputer(Computer computer);

    Optional<Computer> getComputer(long id);

    int getComputerCount();

    int getComputerCount(String searchWord);

    List<Computer> getListComputers();

    List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column, boolean ascending);

    List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column, boolean ascending, String searchWord);

    int getListComputersPageCount(int pageSize);

    int getListComputersPageCount(int pageSize, String searchWord);

    void updateComputer(Computer c);
}
