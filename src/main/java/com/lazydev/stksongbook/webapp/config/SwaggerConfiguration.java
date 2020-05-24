package com.lazydev.stksongbook.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.lazydev.stksongbook.webapp.web.rest"))
        .paths(PathSelectors.ant("/api/**"))
        .build().apiInfo(createApiInfo());
  }

  private ApiInfo createApiInfo() {
    return new ApiInfo("Songbook API", "Tourist songs application", "1.5.5", "Open API",
        contact(), "open source", "", Collections.emptyList());
  }

  private Contact contact() {
    return new Contact("Andrzej Przybysz", "", "andrzej.przybysz01@gmail.com");
  }
}
