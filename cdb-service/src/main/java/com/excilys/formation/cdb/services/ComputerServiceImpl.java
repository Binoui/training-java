package com.excilys.formation.cdb.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.dao.ComputerDAO;
import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.validators.ComputerValidator;
import com.excilys.formation.cdb.validators.IncorrectValidationException;
import com.excilys.formation.cdb.validators.UnknownCompanyIdException;

@Service("ComputerService")
@EnableTransactionManagement
public class ComputerServiceImpl implements ComputerService {

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ComputerValidator.class);

    private ComputerDAO computerDAO;

    private CompanyDAO companyDAO;

    public ComputerServiceImpl(ComputerDAO computerDAO, CompanyDAO companyDAO) {
        this.computerDAO = computerDAO;
        this.companyDAO = companyDAO;
    }

    @Override
    @Transactional
    public Long createComputer(Computer c) throws IncorrectValidationException {
        ComputerValidator.validateComputer(c);
        validateCompany(c.getCompany());
        return computerDAO.createComputer(c);
    }

    @Override
    @Transactional
    public void deleteComputer(Computer c) throws ServiceException {

        if ((c.getId() != null) && computerDAO.getComputer(c).isPresent()) {
            computerDAO.deleteComputer(c);
        } else {
            throw new ServiceException("cannot find computer");
        }
    }

    @Override
    @Transactional
    public void deleteComputers(List<Long> idsToDelete) {
        computerDAO.deleteComputers(idsToDelete);
    }

    @Override
    public Optional<Computer> getComputer(Computer computer) {
        return computerDAO.getComputer(computer);
    }

    @Override
    public Optional<Computer> getComputer(Long id) {
        return computerDAO.getComputer(id);
    }

    @Override
    public int getComputerCount() {
        return computerDAO.getComputerCount();
    }

    @Override
    public int getComputerCount(String searchWord) {
        return computerDAO.getComputerCount(searchWord);
    }

    @Override
    public List<Computer> getComputerPage() {
        return new LinkedList<>();
    }

    @Override
    public List<Computer> getListComputers() {
        return computerDAO.getListComputers();
    }

    @Override
    public List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column,
            boolean ascending) {
        return computerDAO.getListComputers(pageNumber, pageSize, column, ascending);
    }

    @Override
    public List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column,
            boolean ascending, String searchWord) {
        return computerDAO.getListComputers(pageNumber, pageSize, column, ascending, searchWord);
    }

    @Override
    public int getListComputersPageCount(int pageSize) {
        return computerDAO.getListComputersPageCount(pageSize);
    }

    @Override
    public int getListComputersPageCount(int pageSize, String searchWord) {
        return computerDAO.getListComputersPageCount(pageSize, searchWord);
    }

    @Override
    @Transactional
    public void updateComputer(Computer c) throws IncorrectValidationException, ServiceException {
        if ((c.getId() != null) && computerDAO.getComputer(c).isPresent()) {
            ComputerValidator.validateComputer(c);
            validateCompany(c.getCompany());
            computerDAO.updateComputer(c);
        } else {
            throw new ServiceException("cannot find computer");
        }
    }

    private void validateCompany(Optional<Company> optionalCompany) throws UnknownCompanyIdException {

        if (!optionalCompany.isPresent()) {
            return;
        }

        Company company = optionalCompany.get();
        if ((company.getId() != null) && !(companyDAO.getCompany(company).isPresent())) {
            throw new UnknownCompanyIdException("Cannot find given company.");
        }
    }

}
