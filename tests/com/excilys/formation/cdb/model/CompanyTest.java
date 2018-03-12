package com.excilys.formation.cdb.model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

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
		c.setDiscontinued(Date.valueOf("0001-01-01"));
		c.setIntroduced(Date.valueOf("0001-01-02"));
		
		assertEquals(c.getId(), 1);
		assertEquals(c.getName(), "testCompany");
		assertEquals(c.getDiscontinued(), Date.valueOf("0001-01-01"));
		assertEquals(c.getIntroduced(), Date.valueOf("0001-01-02"));
	}
}
