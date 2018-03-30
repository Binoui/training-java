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

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;
import com.excilys.formation.cdb.utils.HSQLDatabase;

public class CompanyDAOTest {

    private CompanyDAO cDAO;

    @After
    public void cleanUp() throws SQLException {
        HSQLDatabase.destroy();
    }

    @Before
    public void setUp() throws SQLException, IOException {
        cDAO = CompanyDAO.INSTANCE;
        HSQLDatabase.initDatabase();
    }

    @Test
    public void testDeleteCompany() throws DAOException {
        cDAO.deleteCompany(1);
        assertFalse(cDAO.getCompany((long) 1).isPresent());
        assertFalse(ComputerDAO.INSTANCE.getComputer(1).isPresent());
    }

    @Test
    public void testGetCompanyCompany() throws DAOException {
        assertNotNull(cDAO.getCompany(new CompanyBuilder().withId((long) 1).build()));
    }

    @Test
    public void testGetCompanyLong() throws DAOException {
        assertNotNull(cDAO.getCompany((long) 1));
    }

    @Test
    public void testGetCompanyWithIdNull() throws DAOException {
        cDAO.getCompany(new CompanyBuilder().build());
    }

    @Test
    public void testGetListCompanies() throws DAOException {
        List<Company> companies = cDAO.getListCompanies();
        assertNotNull(companies);
        assertEquals(companies.size(), 3);
        assertEquals(companies.get(1).getName(), "Company 2");
    }

    @Test
    public void testGetListCompaniesPageCount() {
    }

    @Test
    public void testGetListCompaniesPageOutOfBounds() throws DAOException {
        try {
            cDAO.getListCompanies(999999, 10);
            fail("should throw exception");
        } catch (IndexOutOfBoundsException e) {
        }
    }

}
