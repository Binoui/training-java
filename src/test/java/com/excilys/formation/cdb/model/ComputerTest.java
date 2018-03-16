package com.excilys.formation.cdb.model; 

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

public class ComputerTest {

	private Computer c;
	
	@Before
	public void setUp() {
		c = new Computer();
	}
	
	@Test
	public void testComputer() {
		assertNotNull(c);
		
		c.setId(1);
		c.setName("comp1");
		c.setIntroduced(LocalDate.of(0001, 01, 01));
		c.setDiscontinued(LocalDate.of(0001, 01, 02));
		c.setCompany(new Company((long) 37, "ASUS"));

		assertEquals(c.getId(), new Long(1));
		assertEquals(c.getName(), "comp1");
		assertEquals(c.getDiscontinued(), LocalDate.of(0001, 01, 02));
		assertEquals(c.getIntroduced(), LocalDate.of(0001, 01, 01));
		System.out.println(c);
	}
	
	@Test
	public void testName() {
		c.setName("comp1");
		assertEquals(c.getName(), "comp1");
		c.setName("comp2");
		assertEquals(c.getName(), "comp2");
		c.setName(null);
		assertEquals(c.getName(), null);
	}
	
}
