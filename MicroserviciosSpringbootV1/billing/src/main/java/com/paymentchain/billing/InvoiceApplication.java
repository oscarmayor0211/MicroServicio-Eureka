package com.paymentchain.billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;

@SpringBootApplication
@EnableSwagger2
@EnableWebSecurity
public class InvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceApplication.class, args);
	}




	public ApiInfo apiInfo() {
			return new ApiInfoBuilder().title("EJERCICIO DE DOCUMENTACION")
					.description("se practica la el servicio para documentar las api en swagger")
					.termsOfServiceUrl("")
					.contact(new Contact("Oscar Eduardo Mayor","",""))
					.license("Omayor license")
					.licenseUrl("")
					.version("1.0")
					.build();
	}


	@Bean
	public Docket petApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Documentacion api")
				.apiInfo(apiInfo())
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.paymentchain"))
				.paths(PathSelectors.any())
				.build()
				.tags(new Tag("Billing API","All apis relating to billing service"),
						new Tag("invoice","Make voices"));


	}
	//define api key to include the header
	private ApiKey apiKey(){
		return new ApiKey("JWT","Authorization","header");
	}

	//configure JWT security with global Autorization scope
	private SecurityContext securityContext(){
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth(){
		AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT",authorizationScopes));
	}
}
