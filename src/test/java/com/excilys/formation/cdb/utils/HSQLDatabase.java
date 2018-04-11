package com.excilys.formation.cdb.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Component
public class HSQLDatabase {

    @Autowired
    private DataSource dataSource;

    private static final String DROP_COMPUTER_TABLE = "DROP TABLE computer;";
    private static final String DROP_COMPANY_TABLE = "DROP TABLE company;";

    public void destroy() throws SQLException {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate(DROP_COMPUTER_TABLE);
            statement.executeUpdate(DROP_COMPANY_TABLE);
        }
    }

    public void initDatabase() throws SQLException, IOException {
        try (Connection connection = getConnection();
                InputStream inputStream = HSQLDatabase.class.getResourceAsStream("/hsqldb_script.sql")) {

            SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false,
                    new File("."));
            sqlFile.setConnection(connection);
            try {
                sqlFile.execute();
            } catch (SqlToolError e) {
                e.printStackTrace();
            }
        }
    }
    
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
