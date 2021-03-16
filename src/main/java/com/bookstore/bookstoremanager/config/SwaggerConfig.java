package com.bookstore.bookstoremanager.config;

import org.springframework.context.annotation.*;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger2.annotations.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final String BASE_PACKAGE = "com.bookstore.bookstoremanager";
  private static final String API_TITLE = "Bookstore Manager Course";
  private static final String API_DESCRIPTION = "Bookstore Manager API Professional";
  private static final String API_VERSION = "1.0.0.";
  private static final String CONTACT_NAME = "Allan Alves";
  private static final String CONTACT_GITHUB = "https://github.com/allanalves92?tab=repositories";
  private static final String CONTACT_EMAIL = "allanalves@teste.com.br";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(buildApiInfo());
  }

  private ApiInfo buildApiInfo() {
    return new ApiInfoBuilder()
        .title(API_TITLE)
        .description(API_DESCRIPTION)
        .version(API_VERSION)
        .contact(new Contact(CONTACT_NAME, CONTACT_GITHUB, CONTACT_EMAIL))
        .build();
  }
}
