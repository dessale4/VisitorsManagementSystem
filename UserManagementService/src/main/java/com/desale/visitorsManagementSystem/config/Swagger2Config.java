package com.desale.visitorsManagementSystem.config;

import java.util.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	@Bean 
	public Docket visitorApi() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
////				.apis(RequestHandlerSelectors.any())
//				.apis(RequestHandlerSelectors.basePackage("com.desale.visitorsManagementSystem.controller"))
////				.paths(PathSelectors.any())
//				
//				.paths(PathSelectors.regex("/.*"))
//				.
//				.build().apiInfo(apiDetails());
		Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiDetails()).pathMapping("/")
				.forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class)
				.ignoredParameterTypes(Pageable.class).ignoredParameterTypes(java.sql.Date.class)
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class)
				.securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.newArrayList(apiKey()))
				.useDefaultResponseMessages(false);

		docket = docket.select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.regex("/.*")).build();
		// watch.stop();
		return docket;
	}
	
	private ApiInfo apiDetails() {
//		return new ApiInfo(
//				"UsersServiceApi", 
//				"MicroserviceApi for company visitors",
//				"1.0",
//				"Free to use",
//				new springfox.documentation.service.Contact("Desale Hdremariam", "http://url", "dessale4@gmail.com"),
//				"Api liecense",
//				"http://mypersonalwebsiteUrl",
//				Collections.emptyList()
//				);
				
		return new ApiInfoBuilder()
				.title("UsersServiceApi")
				.description("MicroserviceApi component for company visitors")
				.contact(new Contact("Desale Hdremariam", "https://github.com/dessale4", "dessale4@gmail.com"))
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.version("1.0.0")
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*"))
				.build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
	}
}
