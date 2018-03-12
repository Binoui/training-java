package com.excilys.formation.cdb.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComputerTest {

	private Computer c;
	
	@BeforeEach
	void setUp() {
		c = new Computer();
	}
	
	@Test
	void testComputer() {
		assertNotNull(c);
		
		c.setId(1);
		c.setName("comp1");
		
		assertEquals(c.getId(), 1);
		assertEquals(c.getName(), "comp1");
	}
	
	@Test
	void testName() {
		c.setName("comp1");
		assertEquals(c.getName(), "comp1");
		c.setName("comp2");
		assertEquals(c.getName(), "comp2");
		c.setName(null);
		assertEquals(c.getName(), null);
	}
	
	@Test
	void testCompany() {
		Company com = new Company();
		c.setCompany(com);
		assertEquals(c.getCompany(), com);

	}

}
