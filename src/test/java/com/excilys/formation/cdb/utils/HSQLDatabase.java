package com.excilys.formation.cdb.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class HSQLDatabase {

    private static final DatabaseConnection dbConn = DatabaseConnection.INSTANCE; 
    
    private static final String DROP_COMPUTER_TABLE = "DROP TABLE computer;";
    private static final String DROP_COMPANY_TABLE = "DROP TABLE company;";
    
    public static void destroy() throws SQLException {
        try (Connection connection = dbConn.getConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate(DROP_COMPUTER_TABLE);
            statement.executeUpdate(DROP_COMPANY_TABLE);
        }
    }

    public static void initDatabase() throws SQLException, IOException {
        try (Connection connection = dbConn.getConnection();
                InputStream inputStream = HSQLDatabase.class.getResourceAsStream("/hsqldb_script.sql")) {
            
            SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false, new File("."));
            sqlFile.setConnection(connection); 
            try {
                sqlFile.execute();
            } catch (SqlToolError e) {
                e.printStackTrace();
            }
        }
    }

}
