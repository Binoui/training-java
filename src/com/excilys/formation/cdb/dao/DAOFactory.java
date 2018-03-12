package com.excilys.formation.cdb.dao;

import java.sql.Connection;

import com.excilys.formation.cdb.utils.DatabaseConnection;

public class DAOFactory {

	public static Connection getConnection() {
		return DatabaseConnection.getConnection();
	}
	
	public static ComputerDAO getComputerDAO() {
		return new ComputerDAO();
	}
	
}
