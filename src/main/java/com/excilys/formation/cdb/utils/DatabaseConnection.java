package com.excilys.formation.cdb.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum DatabaseConnection {
    INSTANCE;

    private Connection conn;

    public Connection getConnection() {

        String url = null;
        String user = null;
        String pass = null;
        String driver = null;

        String propertiesPath = "connection.properties";
        InputStream input = null;
        Properties properties = new Properties();

        try {
            input = this.getClass().getClassLoader().getResourceAsStream(propertiesPath);

            properties.load(input);

            url = properties.getProperty("url");
            user = properties.getProperty("user");
            pass = properties.getProperty("pass");
            driver = properties.getProperty("driver");
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
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return conn;
    }

}
