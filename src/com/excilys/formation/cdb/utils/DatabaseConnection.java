package com.excilys.formation.cdb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static DatabaseConnection instance;
	private Connection conn;
	
	private DatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection("localhost", "root", "excilys");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DatabaseConnection getDatabaseConnection() {
		if (instance == null) {
			instance = new DatabaseConnection();
		}
		
		return instance;
	}
}
