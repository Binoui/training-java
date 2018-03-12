package com.excilys.formation.cdb.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

class DatabaseConnectionTest {

	@Test
	void test() {
		Connection conn = DatabaseConnection.getConnection();
		assertNotNull(conn);
	}

}
