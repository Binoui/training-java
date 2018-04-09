package com.excilys.formation.cdb.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.*;
import org.springframework.orm.hibernate5.*;

import com.zaxxer.hikari.HikariDataSource;


@Configuration
@EnableTransactionManagement
public class AppConfig {
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }

    private DataSource dataSource() {
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
        
        final HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(100);
        ds.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        ds.addDataSourceProperty("url", url);
        ds.addDataSourceProperty("user", username);
        ds.addDataSourceProperty("password", password);
        ds.addDataSourceProperty("cachePrepStmts", true);
        ds.addDataSourceProperty("prepStmtCacheSize", 250);
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        ds.addDataSourceProperty("useServerPrepStmts", true);
        return ds;
    }

    private Properties hibernateProperties() {     
        final Properties properties = new Properties();
        return properties;
    }
}