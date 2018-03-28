package com.excilys.formation.cdb.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.dao.ComputerDAO;
import com.excilys.formation.cdb.dao.DAOException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.validators.ComputerValidator;
import com.excilys.formation.cdb.validators.IncorrectValidationException;
import com.excilys.formation.cdb.validators.UnknownComputerIdException;

public enum ComputerService {
    INSTANCE;

    private static ComputerDAO dao = ComputerDAO.INSTANCE;
    private static ComputerValidator validator = ComputerValidator.INSTANCE;

    public Long createComputer(Computer c) throws IncorrectValidationException, ServiceException {
        validator.validateComputer(c);

        try {
            return dao.createComputer(c);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void deleteComputer(Computer c) throws UnknownComputerIdException, ServiceException {
        validator.validateComputerId(c.getId());
        try {
            dao.deleteComputer(c);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void deleteComputers(List<Long> idsToDelete) throws ServiceException {
        try {
            dao.deleteComputers(idsToDelete);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Optional<Computer> getComputer(Computer computer) throws ServiceException {
        try {
            return dao.getComputer(computer);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public int getComputerCount() throws ServiceException {
        try {
            return dao.getComputerCount();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Computer> getComputerPage(int pageNumber) {
        return new LinkedList<Computer>();
    }

    public List<Computer> getListComputers() throws ServiceException {
        try {
            return dao.getListComputers();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Computer> getListComputers(int pageNumber, int pageSize) throws ServiceException {
        try {
            return dao.getListComputers(pageNumber, pageSize);
        } catch (IndexOutOfBoundsException | DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public int getListComputersPageCount(int pageSize) throws ServiceException {
        try {
            return dao.getListComputersPageCount(pageSize);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void updateComputer(Computer c) throws IncorrectValidationException, ServiceException {
        validator.validateComputerId(c.getId());
        validator.validateComputer(c);
        try {
            dao.updateComputer(c);
        } catch (DAOException e) {
            throw new ServiceException("Couldn't update computer " + c.getName());
        }
    }
}
