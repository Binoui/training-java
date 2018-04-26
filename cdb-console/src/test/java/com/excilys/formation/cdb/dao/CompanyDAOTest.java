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
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.utils.HSQLDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConsoleConfig.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("cli")
public class CompanyDAOTest {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;
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
        companyService.deleteCompany((long) 1);
        assertFalse(companyService.getCompany((long) 1).isPresent());
        assertFalse(computerService.getComputer((long) 1).isPresent());
    }

    @Test
    public void testGetCompanyLong() {
        assertNotNull(companyService.getCompany((long) 1));
    }

    @Test
    public void testGetListCompanies() {
        List<Company> companies = companyService.getListCompanies();
        assertNotNull(companies);
        assertEquals(3, companies.size());
        assertEquals("Company 2", companies.get(1).getName());
    }

    @Test
    public void testGetListCompaniesPageCount() {
        assertEquals(1, companyService.getListCompaniesPageCount(10));
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
