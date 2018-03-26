package com.excilys.formation.cdb.dao;

import static org.junit.Assert.*;

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
    
    @Before
    public void setUp() throws SQLException, IOException {
        HSQLDatabase.initDatabase();
    }
    
    @After
    public void cleanUp() throws SQLException {
        HSQLDatabase.destroy();
    }

    @Test
    public void testCreateComputer() {
        Computer c = new ComputerBuilder().withName("testComputer").withIntroduced(null).withDiscontinued(LocalDate.parse("0002/02/02")).build();
        cDao.createComputer(c);
        assertNotNull(cDao.getComputer(c));
        cDao.deleteComputer(c);
    }

    @Test
    public void testDeleteComputer() {
        Computer c = new ComputerBuilder().withName("testComputer").withIntroduced(null).withDiscontinued(LocalDate.parse("0002/02/02")).build();
        cDao.createComputer(c);
        cDao.deleteComputer(c);
        assertTrue(cDao.getComputer(c).isPresent());
    }

    @Test
    public void testGetComputer() {
        assertTrue(cDao.getComputer(new ComputerBuilder().withId((long) 1).build()).isPresent());
    }

    @Test
    public void testGetComputerCount() {
        assertEquals(cDao.getComputerCount(), 3);
    }

    @Test
    public void testGetListComputers() {
        assertEquals((long) cDao.getListComputers().get(1).getId(), (long) 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetListComputersIntInt() {
        cDao.getListComputers(99999, 10);
    }

    @Test
    public void testGetListComputersPageCount() {
        assertEquals(cDao.getListComputersPageCount(10), 1);
    }

    @Test
    public void testUpdateComputer() {
        Computer c = new ComputerBuilder().withId((long) 2).withIntroduced(LocalDate.parse("0001/01/01")).build();
        cDao.updateComputer(c);
        assertEquals(cDao.getComputer(c).get().getIntroduced(), LocalDate.parse("0001/01/01")); 
    }

}
