package com.excilys.formation.cdb.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

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
		c.setIntroduced(LocalDate.of(0001, 01, 01));
		c.setDiscontinued(LocalDate.of(0001, 01, 02));
		c.setCompany((long) 1);

		assertEquals(c.getId(), 1);
		assertEquals(c.getName(), "comp1");
		assertEquals(c.getDiscontinued(), LocalDate.of(0001, 01, 01));
		assertEquals(c.getIntroduced(), LocalDate.of(0001, 01, 02));
		System.out.println(c);
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
	
}
