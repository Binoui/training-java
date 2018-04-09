package com.excilys.formation.cdb.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.utils.HSQLDatabase;

public class ComputerDAOTest {

    private static final ComputerDAO cDao = ComputerDAO.INSTANCE;

    @After
    public void cleanUp() throws SQLException {
        HSQLDatabase.destroy();
    }

    @Before
    public void setUp() throws SQLException, IOException {
        HSQLDatabase.initDatabase();
    }

    @Test
    public void testCreateComputer() throws DAOException {
        Computer c = new ComputerBuilder().withName("testComputer").withIntroduced(null)
                .withDiscontinued(LocalDate.parse("0002-02-02")).build();
        c.setId(cDao.createComputer(c));
        assertTrue(cDao.getComputer(c).isPresent());
        cDao.deleteComputer(c);
    }

    @Test
    public void testDeleteComputer() throws DAOException {
        Computer c = new ComputerBuilder().withName("testComputer").withIntroduced(null)
                .withDiscontinued(LocalDate.parse("0002-02-02")).build();
        c.setId(cDao.createComputer(c));
        cDao.deleteComputer(c);
        assertFalse(cDao.getComputer(c).isPresent());
    }

    @Test
    public void testGetCompanyWithIdNull() throws DAOException {
        cDao.getComputer(new ComputerBuilder().build());
    }

    @Test
    public void testGetComputer() throws DAOException {
        assertTrue(cDao.getComputer(new ComputerBuilder().withId((long) 1).build()).isPresent());
    }

    @Test
    public void testGetComputerCount() throws DAOException {
        assertEquals(cDao.getComputerCount(), 3);
    }

    @Test
    public void testGetListComputers() throws DAOException {
        assertEquals((long) cDao.getListComputers().get(1).getId(), (long) 2);
    }

    @Test
    public void testGetListComputersPageCount() throws DAOException {
        assertEquals(cDao.getListComputersPageCount(10), 1);
    }

    @Test
    public void testSearch() throws IndexOutOfBoundsException, DAOException {
        assertEquals(cDao.getListComputers(0, 10, SortableComputerColumn.ID, true, "Computer").size(), 2);
    }

    @Test
    public void testUpdateComputer() throws DAOException {
        Computer c = new ComputerBuilder().withId((long) 2).withIntroduced(LocalDate.parse("0001-01-01")).build();
        cDao.updateComputer(c);
        assertEquals(cDao.getComputer(c).get().getIntroduced(), LocalDate.parse("0001-01-01"));
    }

}
