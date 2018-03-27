package com.excilys.formation.cdb.validators;

import java.time.LocalDate;

import com.excilys.formation.cdb.dao.CompanyDAO;
import com.excilys.formation.cdb.dao.ComputerDAO;
import com.excilys.formation.cdb.dao.DAOException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;;

public enum ComputerValidator {
    INSTANCE;

    private CompanyDAO companyDAO = CompanyDAO.INSTANCE;
    private ComputerDAO computerDAO = ComputerDAO.INSTANCE;

    private void validateCompany(Company company) throws UnknownCompanyIdException {
        try {
            if ((company != null) && (company.getId() != null) && (companyDAO.getCompany(company) == null)) {
                throw new UnknownCompanyIdException("Cannot find given company.");
            }
        } catch (DAOException e) {
            throw new UnknownCompanyIdException("Cannot find given company.");
        }
    }

    public void validateComputer(Computer c) throws IncorrectValidationException {
        validateName(c.getName());
        validateDates(c.getIntroduced(), c.getDiscontinued());
        validateCompany(c.getCompany());
    }

    public void validateComputerId(Long id) throws UnknownComputerIdException {
        try {
            if ((id != null) && (computerDAO.getComputer(new ComputerBuilder().withId(id).build()) == null)) {
                throw new UnknownComputerIdException("Cannot find given computer.");
            }
        } catch (DAOException e) {
            throw new UnknownComputerIdException("Cannot find given computer.");
        }
    }

    private void validateDates(LocalDate introduced, LocalDate discontinued) throws InvalidDatesException {
        if ((discontinued != null) && (introduced != null) && !introduced.isBefore(discontinued)) {
            throw new InvalidDatesException("Discontinued date cannot be before introducted date.");
        }
    }

    private void validateName(String name) throws NullNameException {
        if ((name == null) || name.isEmpty()) {
            throw new NullNameException("Computer name cannot be null.");
        }
    }
}
