package com.excilys.formation.cdb.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.LoggerFactory;

public enum DatabaseConnection {
    INSTANCE;

    private Connection conn;

    public Connection getConnection() {

        String url = null;
        String user = null;
        String pass = null;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String propertiesPath = "/src/main/ressources/connection.properties";
        InputStream input = null;
        Properties properties = new Properties();

        try {
            input = classLoader.getResourceAsStream(propertiesPath);
            
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
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

}
