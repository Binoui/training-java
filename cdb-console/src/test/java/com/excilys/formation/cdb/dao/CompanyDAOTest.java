package com.excilys.formation.cdb.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.excilys.formation.cdb.config.ConsoleConfig;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;
import com.excilys.formation.cdb.utils.HSQLDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConsoleConfig.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("cli")
public class CompanyDAOTest {

    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private ComputerDAO computerDAO;
    @Autowired
    private HSQLDatabase hSqlDatabase;

    @After
    public void cleanUp() throws SQLException {
        hSqlDatabase.destroy();
    }

    @Before
    public void setUp() throws SQLException, IOException {
        hSqlDatabase.initDatabase();
    }

    @Test
    public void testDeleteCompany() {
        companyDAO.deleteCompany(1);
        assertFalse(companyDAO.getCompany((long) 1).isPresent());
        assertFalse(computerDAO.getComputer(1).isPresent());
    }

    @Test
    public void testGetCompanyCompany() {
        assertNotNull(companyDAO.getCompany(new CompanyBuilder().withId((long) 1).build()));
    }

    @Test
    public void testGetCompanyLong() {
        assertNotNull(companyDAO.getCompany((long) 1));
    }

    @Test
    public void testGetCompanyWithIdNull() {
        companyDAO.getCompany(new CompanyBuilder().build());
    }

    @Test
    public void testGetListCompanies() {
        List<Company> companies = companyDAO.getListCompanies();
        assertNotNull(companies);
        assertEquals(3, companies.size());
        assertEquals("Company 2", companies.get(1).getName());
    }

    @Test
    public void testGetListCompaniesPageCount() {
        assertEquals(1, companyDAO.getListCompaniesPageCount(10));
    }

    // @Test
    // public void testGetListCompaniesPageOutOfBounds() {
    // try {
    // companyDAO.getListCompanies(999999, 10);
    // fail("should throw exception");
    // } catch (DAOException e) {
    // }
    // }

}
