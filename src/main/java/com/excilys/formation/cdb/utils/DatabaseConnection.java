package com.excilys.formation.cdb.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

public enum DatabaseConnection {
    INSTANCE;

    private static final Logger Logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private static final String PROPERTIES_PATH = "connection.properties";

    private String driver;
    private HikariDataSource hikariDataSource;

    DatabaseConnection() {
        InputStream input = null;
        Properties properties = new Properties();

        String url = null;
        String user = null;
        String pass = null;

        try {
            input = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH);

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

        hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(user);
        hikariDataSource.setPassword(pass);
        hikariDataSource.setMaxLifetime(60000);
    }

    public Connection getConnection() {

        Connection conn = null;

        try {
            Class.forName(driver);
            conn = hikariDataSource.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            Logger.error("cannot connect to database [}", e);
        }

        return conn;
    }

}
