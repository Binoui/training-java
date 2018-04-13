package com.excilys.formation.cdb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;
import com.excilys.formation.cdb.utils.HSQLDatabase;
import com.excilys.formation.cdb.validators.IncorrectValidationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ComputerServiceTest {

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
    public void testCreateComputer() throws ServiceException, IncorrectValidationException {
        Computer c = new ComputerBuilder().withName("testComputer").withIntroduced(null)
                .withDiscontinued(LocalDate.parse("0002-02-02")).build();
        c.setId(computerService.createComputer(c));
        assertTrue(computerService.getComputer(c).isPresent());
        computerService.deleteComputer(c);
    }

    @Test
    public void testDeleteComputer() throws ServiceException, IncorrectValidationException {
        Computer c = new ComputerBuilder().withName("testComputer").withIntroduced(null)
                .withDiscontinued(LocalDate.parse("0002-02-02")).build();
        c.setId(computerService.createComputer(c));
        computerService.deleteComputer(c);
        assertFalse(computerService.getComputer(c).isPresent());
    }
    
    @Test
    public void testDeleteComputers() throws ServiceException {
        computerService.deleteComputers(Arrays.asList(1L, 3L));
        computerService.deleteComputers(Arrays.asList(2L, 3444L));
        assertTrue(computerService.getComputer(new ComputerBuilder().withId((long) 2).build()).isPresent());
    }

    @Test
    public void testGetCompanyWithIdNull() throws ServiceException {
        computerService.getComputer(new ComputerBuilder().build());
    }

    @Test
    public void testGetComputer() throws ServiceException {
        assertTrue(computerService.getComputer(new ComputerBuilder().withId((long) 1).build()).isPresent());
    }

    @Test
    public void testGetComputerCount() throws ServiceException {
        assertEquals(computerService.getComputerCount(), 3);
    }

    @Test
    public void testGetListComputers() throws ServiceException {
        assertEquals((long) computerService.getListComputers().get(1).getId(), (long) 2);
    }

    @Test
    public void testGetListComputersPageCount() throws ServiceException {
        assertEquals(computerService.getListComputersPageCount(10), 1);
    }

    @Test
    public void testSearch() throws IndexOutOfBoundsException, ServiceException {
        assertEquals(computerService.getListComputers(0, 10, SortableComputerColumn.ID, true, "Computer").size(), 2);
    }

    @Test
    public void testUpdateComputer() throws ServiceException, IncorrectValidationException {
        Computer c = new ComputerBuilder().withId((long) 2).withName("Computer 2").withIntroduced(LocalDate.parse("0001-01-01")).build();
        computerService.updateComputer(c);
        assertEquals(computerService.getComputer(c).get().getIntroduced(), LocalDate.parse("0001-01-01"));
    }

}
