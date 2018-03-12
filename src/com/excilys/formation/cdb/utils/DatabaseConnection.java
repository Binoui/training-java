package com.excilys.formation.cdb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
			String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
			String user = "root";
			String pass = "excilys";
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (instance == null) {
			instance = new DatabaseConnection();
		}

		return instance.conn;
	}

	public static void closeConnection(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ignore) {
		}
	}
}
