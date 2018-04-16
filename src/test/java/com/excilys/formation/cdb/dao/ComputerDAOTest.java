package com.excilys.formation.cdb.dao;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.utils.HSQLDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ComputerDAOTest {

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
    public void testCreateComputer() throws DAOException {
        Computer c = new ComputerBuilder().withName("testComputer").withIntroduced(null)
                .withDiscontinued(LocalDate.parse("0002-02-02")).build();
        c.setId(computerDAO.createComputer(c));
        assertTrue(computerDAO.getComputer(c).isPresent());
        computerDAO.deleteComputer(c);
    }

    @Test
    public void testDeleteComputer() throws DAOException {
        Computer c = new ComputerBuilder().withName("testComputer").withIntroduced(null)
                .withDiscontinued(LocalDate.parse("0002-02-02")).build();
        c.setId(computerDAO.createComputer(c));
        computerDAO.deleteComputer(c);
        assertFalse(computerDAO.getComputer(c).isPresent());
    }

    @Test
    public void testDeleteComputers() throws DAOException {
        computerDAO.deleteComputers(Arrays.asList(1L, 3L));
    }

    @Test
    public void testGetCompanyWithIdNull() throws DAOException {
        computerDAO.getComputer(new ComputerBuilder().build());
    }

    @Test
    public void testGetComputer() throws DAOException {
        assertTrue(computerDAO.getComputer(new ComputerBuilder().withId((long) 1).build()).isPresent());
    }

    @Test
    public void testGetComputerCount() throws DAOException {
        assertEquals(3, computerDAO.getComputerCount());
    }

    @Test
    public void testGetComputerCountSearch() throws DAOException {
        assertEquals(2, computerDAO.getComputerCount("Computer"));
        assertEquals(1, computerDAO.getComputerCount("Computer 1"));
        assertEquals(0, computerDAO.getComputerCount("zepc,pfz,^^$efze"));
    }

    @Test
    public void testGetListComputers() throws DAOException {
        assertEquals((long) computerDAO.getListComputers().get(1).getId(), (long) 2);
    }

    @Test
    public void testGetListComputersPageCount() throws DAOException {
        assertEquals(computerDAO.getListComputersPageCount(10), 1);
    }

    @Test
    public void testGetListComputersSorted() throws DAOException {
        assertEquals(2,
                (long) computerDAO.getListComputers(0, 10, SortableComputerColumn.INTRODUCED, false).get(0).getId());
        assertEquals(1,
                (long) computerDAO.getListComputers(0, 10, SortableComputerColumn.INTRODUCED, false).get(2).getId());
    }

    @Test
    public void testSearch() throws IndexOutOfBoundsException, DAOException {
        assertEquals(computerDAO.getListComputers(0, 10, SortableComputerColumn.ID, true, "Computer").size(), 2);
    }

    @Test
    public void testUpdateComputer() throws DAOException {
        Computer c = new ComputerBuilder().withId((long) 2).withIntroduced(LocalDate.parse("0001-01-01")).build();
        computerDAO.updateComputer(c);
        assertEquals(LocalDate.parse("0001-01-01"), computerDAO.getComputer(c).get().getIntroduced().orElse(null));
    }

}
