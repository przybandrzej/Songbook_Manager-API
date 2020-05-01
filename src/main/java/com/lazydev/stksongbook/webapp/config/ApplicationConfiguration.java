package com.lazydev.stksongbook.webapp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.lazydev.stksongbook.webapp")
@EnableConfigurationProperties(StorageProperties.class)
public class ApplicationConfiguration {

}
