package com.excilys.formation.cdb.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyTest {

	private Company c;
	
	@BeforeEach
	void setUp() {
		c = new Company();
	}
	
	@Test
	void testCompany() {
		assertNotNull(c);
		
		c.setId(1);
		c.setName("testCompany");
		
		assertEquals(c.getId(), 1);
		assertEquals(c.getName(), "testCompany");
		System.out.println(c);
	}
}
