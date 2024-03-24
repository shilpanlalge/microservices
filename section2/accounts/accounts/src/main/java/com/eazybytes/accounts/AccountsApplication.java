package com.eazybytes.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
//@ComponentScans({@ComponentScan("com.easybytes.accounts.controller")})
//@EnableJpaRepositories("com.easybytes.accounts.repository")
//@EntityScan("com.easybytes.accounts.model")
@EnableJpaAuditing(auditorAwareRef= "auditAwareImpl")
@OpenAPIDefinition(
		info=@Info(title="Accounts microservice REST API Docementation",
		description="EasyBank Accounts microservice REST API Docementation",
		version="v1",
		contact=@Contact(
				name="shilpa Naveen",
				email="shilpalalge@gmail.com",
				url="http://www.easybank.com"
				),
		license=@License(
				name="apache 2.0",
				url="http://www.easybank.com"
				)
		),
		externalDocs=@ExternalDocumentation(
				description="EasyBank Accounts microservice REST API Docementation",
					url="http://www.easybank.com/swagger-ui.html")
		)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
