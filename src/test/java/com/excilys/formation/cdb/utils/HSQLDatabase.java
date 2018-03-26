package com.excilys.formation.cdb.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public enum HSQLDatabase {
    INSTANCE;

    private static final DatabaseConnection dbConn = DatabaseConnection.INSTANCE; 
    
    private static final String DROP_COMPUTER_TABLE = "DROP TABLE computer;";
    private static final String DROP_COMPANY_TABLE = "DROP TABLE company;";
    private static final String CREATE_COMPANY_TABLE = "CREATE TABLE company (ca_id BIGINT NOT NULL AUTO_INCREMENT, "
                    + "ca_name VARCHAR(255) NOT NULL," + "CONSTRAINT pk_company PRIMARY KEY (ca_id));";
    private static final String CREATE_COMPUTER_TABLE = "CREATE TABLE computer (" + "cu_id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "cu_name VARCHAR(255)," + "cu_introduced DATETIME NULL," + "cu_discontinued DATETIME NULL,"
                    + "ca_id BIGINT DEFAULT NULL," + "constraint pk_computer PRIMARY KEY (cu_id));";
    
    private static final String INSERT_COMPANY = "INSERT INTO company (id, name) VALUES (?, ?);"; 
    private static final String INSERT_COMPUTER = "insert into computer (id,name,introduced,discontinued,company_id) VALUES (?, ?, ?, ?, ?);"; 
    
    @AfterClass
    public static void destroy() throws SQLException {
        try (Connection connection = dbConn.getConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate(DROP_COMPUTER_TABLE);
            statement.executeUpdate(DROP_COMPANY_TABLE);
        }
    }

    @BeforeClass
    public static void initDatabase() throws SQLException {
        try (Connection connection = dbConn.getConnection();) {
            
            connection.createStatement().execute(CREATE_COMPANY_TABLE);

            PreparedStatement statementInsertCompanies = connection.prepareStatement(INSERT_COMPANY);
            statementInsertCompanies.setInt(1, 1);
            statementInsertCompanies.setString(2, "Company 1");
            statementInsertCompanies.executeUpdate();
            
            statementInsertCompanies.setInt(1, 2);
            statementInsertCompanies.setString(2, "Company 2");
            statementInsertCompanies.executeUpdate();

            statementInsertCompanies.setInt(1, 3);
            statementInsertCompanies.setString(2, "Company 3");
            statementInsertCompanies.executeUpdate();

            connection.createStatement().execute(CREATE_COMPUTER_TABLE);

            PreparedStatement statementInsertComputers = connection.prepareStatement(INSERT_COMPUTER);
            
            statementInsertComputers.setInt(1, 1);
            statementInsertComputers.setString(2, "Computer 1");
            statementInsertComputers.setDate(3, Date.valueOf("01/01/0001"));
            statementInsertComputers.setDate(4, Date.valueOf("02/01/0001"));
            statementInsertComputers.setInt(5, 1);
            
            statementInsertComputers.setInt(1, 2);
            statementInsertComputers.setString(2, "Computer 2");
            statementInsertComputers.setNull(3, java.sql.Types.DATE);
            statementInsertComputers.setNull(4, java.sql.Types.DATE);
            statementInsertComputers.setNull(5, java.sql.Types.BIGINT);

            statementInsertComputers.setInt(1, 3);
            statementInsertComputers.setString(2, "Computer 3");
            statementInsertComputers.setNull(3, java.sql.Types.DATE);
            statementInsertComputers.setDate(3, Date.valueOf("01/01/0001"));
            statementInsertComputers.setInt(5, 3);
            
        }
    }

}
