package com.excilys.formation.cdb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class HSQLConnection {

    @BeforeClass
    public static void init() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        
        initDatabase();
    }
    
    @AfterClass
    public static void destroy() throws SQLException {
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();) {
            statement.executeUpdate("DROP TABLE computer;");
            statement.executeUpdate("DROP TABLE company;");
        }
    }
    
    private static void initDatabase() throws SQLException {
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement(); ) {
            statement.execute("CREATE TABLE company (ca_id BIGINT NOT NULL AUTO_INCREMENT, "
                    + "ca_name VARCHAR(255) NOT NULL,"
                    + "CONSTRAINT pk_company PRIMARY KEY (ca_id));");

            statement.executeUpdate("insert into company (id,name) values (1,'Company 1');");
            statement.executeUpdate("insert into company (id,name) values (2,'Company 2');");
            statement.executeUpdate("insert into company (id,name) values (3,'Company 3');");
            
            statement.execute("CREATE TABLE computer ("
                    + "cu_id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "cu_name VARCHAR(255),"
                    + "cu_introduced DATETIME NULL,"
                    + "cu_discontinued DATETIME NULL,"
                    + "ca_id BIGINT DEFAULT NULL,"
                    + "constraint pk_computer PRIMARY KEY (cu_id));");
            
            statement.executeUpdate("insert into computer (id,name,introduced,discontinued,company_id) VALUES " 
                + "(1,'Computer 1', 01/01/0001, 02/01/0001, 1);");

            statement.executeUpdate("insert into computer (id,name,introduced,discontinued,company_id) VALUES " 
                + "(2,'Computer 2', null, null, null);");
            
            statement.executeUpdate("insert into computer (id,name,introduced,discontinued,company_id) VALUES " 
                + "(3,'Computer 3', null, 02/01/0001, 3);");

        }
    }
    
    public static Connection getConnection() throws SQLException {
        initDatabase();
        return DriverManager.getConnection("jdbc:hsqldb:mem:cdb", "root", "root");
    }
    
}
