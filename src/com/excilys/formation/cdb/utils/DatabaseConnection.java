package com.excilys.formation.cdb.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.sql.SQLException;

public class DatabaseConnection {

	private static DatabaseConnection instance;
	private Connection conn;

	private DatabaseConnection() {
		String url = null;
		String user = null;
		String pass = null;	

		String propertiesPath = "connection.propeties";
		InputStream input = null;
		Properties properties = new Properties();

		try {
			input = new FileInputStream(propertiesPath);
			properties.load(input);
			
			url = properties.getProperty("url");			
			user = properties.getProperty("user");
			pass = properties.getProperty("pass");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
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
