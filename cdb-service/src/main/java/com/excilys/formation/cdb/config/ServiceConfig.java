package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.excilys.formation.cdb.config.PersistenceConfig;

@Configuration
@Import(PersistenceConfig.class)
@ComponentScan({ "com.excilys.formation.cdb.services"})
public class ServiceConfig {

    private PersistenceConfig persistenceConfig;
    
    public ServiceConfig(PersistenceConfig persistenceConfig) {
        this.persistenceConfig = persistenceConfig;
    }

    @Bean
    public DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(persistenceConfig.dataSource());
    }
}
