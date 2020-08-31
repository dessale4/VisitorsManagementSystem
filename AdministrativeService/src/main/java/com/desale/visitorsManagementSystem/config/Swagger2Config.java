package com.desale.visitorsManagementSystem.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	
	@Bean 
	public Docket visitorApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
//				.apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.desale.visitorsManagementSystem.controller"))
//				.paths(PathSelectors.any()) 
				.paths(PathSelectors.regex("/api/.*"))
				.build().apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails() {
		return new ApiInfo(
				"AdministrativeServiceApi", 
				"MicroserviceApi for company visitors",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("Desale Hdremariam", "http://url", "dessale4@gmail.com"),
				"Api liecense",
				"http://mypersonalwebsiteUrl",
				Collections.emptyList()
				);
				
	}
}
