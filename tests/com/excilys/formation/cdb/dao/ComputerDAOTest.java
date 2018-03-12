package com.excilys.formation.cdb.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.dao.ComputerDAO;

class ComputerDAOTest {

	@Test
	void test() {
		List<Computer> computers = new ComputerDAO().listComputers();
		assertFalse(computers.isEmpty());
		System.out.println(computers.size());
	}

}
