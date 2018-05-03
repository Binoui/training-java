package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Profile("web")
@Import(ServiceConfig.class)
@ComponentScan(basePackages = { "com.excilys.formation.cdb.config", "com.excilys.formation.cdb.controllers.web" })
public class WebConfig implements WebMvcConfigurer {
}