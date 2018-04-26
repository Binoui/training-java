package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.formation.cdb.config.PersistenceConfig;

@Configuration
@Import(PersistenceConfig.class)
@ComponentScan({ "com.excilys.formation.cdb.services"})
@EnableTransactionManagement
public class ServiceConfig {
}
