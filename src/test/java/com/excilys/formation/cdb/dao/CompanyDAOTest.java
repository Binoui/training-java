package com.excilys.formation.cdb.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;
import com.excilys.formation.cdb.utils.HSQLDatabase;

public class CompanyDAOTest {

    @Autowired
    private CompanyDAOImpl companyDAO;
    @Autowired
    private ComputerDAOImpl computerDAO;

    @After
    public void cleanUp() throws SQLException {
        HSQLDatabase.destroy();
    }

    @Before
    public void setUp() throws SQLException, IOException {
        HSQLDatabase.initDatabase();
    }

    @Test
    public void testDeleteCompany() throws DAOException {
        companyDAO.deleteCompany(1);
        assertFalse(companyDAO.getCompany((long) 1).isPresent());
        assertFalse(computerDAO.getComputer(1).isPresent());
    }

    @Test
    public void testGetCompanyCompany() throws DAOException {
        assertNotNull(companyDAO.getCompany(new CompanyBuilder().withId((long) 1).build()));
    }

    @Test
    public void testGetCompanyLong() throws DAOException {
        assertNotNull(companyDAO.getCompany((long) 1));
    }

    @Test
    public void testGetCompanyWithIdNull() throws DAOException {
        companyDAO.getCompany(new CompanyBuilder().build());
    }

    @Test
    public void testGetListCompanies() throws DAOException {
        List<Company> companies = companyDAO.getListCompanies();
        assertNotNull(companies);
        assertEquals(3, companies.size());
        assertEquals("Company 2", companies.get(1).getName());
    }

    @Test
    public void testGetListCompaniesPageCount() {
    }

    @Test
    public void testGetListCompaniesPageOutOfBounds() throws DAOException {
        try {
            companyDAO.getListCompanies(999999, 10);
            fail("should throw exception");
        } catch (DAOException e) {
        }
    }

}