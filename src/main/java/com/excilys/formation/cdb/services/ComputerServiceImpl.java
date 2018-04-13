package com.excilys.formation.cdb.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.dao.ComputerDAO;
import com.excilys.formation.cdb.dao.DAOException;
import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.validators.ComputerValidator;
import com.excilys.formation.cdb.validators.IncorrectValidationException;
import com.excilys.formation.cdb.validators.UnknownCompanyIdException;
import com.excilys.formation.cdb.validators.UnknownComputerIdException;

@Service("ComputerService")
@EnableTransactionManagement
public class ComputerServiceImpl implements ComputerService {

    private ComputerDAO computerDAO;

    private CompanyDAO companyDAO;
    
    @Autowired
    public ComputerServiceImpl(ComputerDAO computerDAO, CompanyDAO companyDAO) {
        this.computerDAO = computerDAO;
        this.companyDAO = companyDAO;
    }

    @Override
    public Long createComputer(Computer c) throws IncorrectValidationException, ServiceException {
        ComputerValidator.validateComputer(c);
        validateCompany(c.getCompany());

        try {
            return computerDAO.createComputer(c);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteComputer(Computer c) throws UnknownComputerIdException, ServiceException {

        try {
            if ((c.getId() != null) && computerDAO.getComputer(c).isPresent()) {
                computerDAO.deleteComputer(c);
            } else {
                throw new ServiceException("cannot find computer");
            }

        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional(value="txManager", rollbackFor=ServiceException.class)
    public void deleteComputers(List<Long> idsToDelete) throws ServiceException {
        try {
            computerDAO.deleteComputers(idsToDelete);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<Computer> getComputer(Computer computer) throws ServiceException {
        try {
            return computerDAO.getComputer(computer);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int getComputerCount() throws ServiceException {
        try {
            return computerDAO.getComputerCount();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int getComputerCount(String searchWord) throws ServiceException {
        try {
            return computerDAO.getComputerCount(searchWord);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Computer> getComputerPage() {
        return new LinkedList<>();
    }

    @Override
    public List<Computer> getListComputers() throws ServiceException {
        try {
            return computerDAO.getListComputers();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column,
            boolean ascending) throws ServiceException {
        try {
            return computerDAO.getListComputers(pageNumber, pageSize, column, ascending);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column,
            boolean ascending, String searchWord) throws ServiceException {
        try {
            return computerDAO.getListComputers(pageNumber, pageSize, column, ascending, searchWord);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int getListComputersPageCount(int pageSize) throws ServiceException {
        try {
            return computerDAO.getListComputersPageCount(pageSize);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int getListComputersPageCount(int pageSize, String searchWord) throws ServiceException {
        try {
            return computerDAO.getListComputersPageCount(pageSize, searchWord);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void updateComputer(Computer c) throws IncorrectValidationException, ServiceException {

        try {
            if ((c.getId() != null) && computerDAO.getComputer(c).isPresent()) {
                ComputerValidator.validateComputer(c);
                validateCompany(c.getCompany());
                computerDAO.updateComputer(c);
            } else {
                throw new ServiceException("cannot find computer");
            }
        } catch (DAOException e) {
            throw new ServiceException("Couldn't update computer " + c.getName());
        }
    }

    private void validateCompany(Company company) throws UnknownCompanyIdException {
        try {
            if ((company != null) && (company.getId() != null) && !(companyDAO.getCompany(company).isPresent())) {
                throw new UnknownCompanyIdException("Cannot find given company.");
            }
        } catch (DAOException e) {
            throw new UnknownCompanyIdException("Cannot find given company.");
        }
    }

}
