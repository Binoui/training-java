package com.excilys.formation.cdb.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.dao.ComputerDAO;

class ComputerDAOTest {

	@Test
	void test() {
		ComputerDAO cDAO = new ComputerDAO();
		
		List<Computer> computers = cDAO.listComputers();
		assertFalse(computers.isEmpty());
		assertEquals(computers.size(), 574);

		int pageSize = 10;
		int pageCount = cDAO.getPageCount(pageSize);
		assertEquals(pageCount, computers.size() / pageSize);
		
		computers = cDAO.listComputers(0, pageSize);
		assertEquals(computers.size(), 10);

		computers = cDAO.listComputers(1, pageSize);
		assertEquals(computers.get(0).getId(), 11);

		try {
			computers = cDAO.listComputers(60, pageSize);
			assertTrue(false);
		} catch (IndexOutOfBoundsException i) {
			// supposed to go here
			assertTrue(true);
		}
	}
}
