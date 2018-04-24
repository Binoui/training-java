package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import com.excilys.formation.cdb.config.ServiceConfig;

@Configuration
@Profile("cli")
@Import(ServiceConfig.class)
@ComponentScan({"com.excilys.formation.cdb.controllers.cli"})
public class ConsoleConfig {
}