package com.excilys.formation.cdb.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.dao.ComputerDAO;

class ComputerDAOTest {

	private ComputerDAO cDAO;
	
	@BeforeEach
	void setUp() {
		cDAO = ComputerDAO.INSTANCE; 
	}
	
	@Test
	void test() {
		
		List<Computer> computers = cDAO.getListComputers();
		assertFalse(computers.isEmpty());
		assertEquals(computers.size(), 574);

		int pageSize = 10;
		int pageCount = cDAO.getPageCount(pageSize);
		assertEquals(pageCount, computers.size() / pageSize);
		
		computers = cDAO.getListComputers(0, pageSize);
		assertEquals(computers.size(), 10);

		computers = cDAO.getListComputers(1, pageSize);
		assertEquals(computers.get(0).getId(), 11);

		try {
			computers = cDAO.getListComputers(60, pageSize);
			assertTrue(false);
		} catch (IndexOutOfBoundsException i) {
			assertTrue(true);
		}
	}
	
	@Test
	void createUpdateDeleteTest() {
		List<Computer> computers = cDAO.getListComputers();
		int oldSize = computers.size();

		Computer c = new Computer();
		c.setName("testName");
		c.setIntroduced(LocalDate.of(0001, 01, 01));
		c.setDiscontinued(LocalDate.of(0001, 01, 02));
		c.setCompany(new Company((long) 37, "ASUS"));

		cDAO.createComputer(c);

		computers = cDAO.getListComputers();
		assertEquals(oldSize + 1, computers.size());

		Computer newC = computers.get(oldSize);
		assertEquals(newC.getName(), c.getName());
		
		String newName = "newName";
		newC.setName(newName);
		cDAO.updateComputer(newC);

		newC = cDAO.getListComputers().get(oldSize);
		assertNotEquals(newC.getName(), c.getName());

		if (newC.getName().equals(newName)) {
			cDAO.deleteComputer(newC);
			computers = cDAO.getListComputers();
			assertEquals(oldSize, computers.size());
		}
		else {
			// couldn't delete
			assertTrue(false);
		}
		
	}
}
