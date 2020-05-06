package com.lazydev.stksongbook.webapp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.lazydev.stksongbook.webapp")
@EnableConfigurationProperties(StorageProperties.class)
@EnableTransactionManagement
public class ApplicationConfiguration {

}
