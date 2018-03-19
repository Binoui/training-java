package com.excilys.formation.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class CompanyTest {

    private Company c;

    @Before
    public void setUp() {
        c = new Company();
    }

    @Test
    public void testCompany() {
        assertNotNull(c);

        c.setId(new Long(1));
        c.setName("testCompany");

        assertEquals(c.getId(), new Long(1));
        assertEquals(c.getName(), "testCompany");
        System.out.println(c);
    }
}
