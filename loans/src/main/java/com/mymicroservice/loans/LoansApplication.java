package com.mymicroservice.loans;

import com.mymicroservice.loans.dto.LoansContactDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactDto.class})
@OpenAPIDefinition(
		info = @Info(
			title = "Loans microservice REST API Documentation",
			description = "Bank Loans microservice REST API Documentation",
			version = "v1",
				contact = @Contact(
						name = "Md Asif Shamim",
						email = "asifshmim0403@gmail.com",
						url = "https://linkedin.com/in/md-asif-shamim/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://tomcat.apache.org/"
				)
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
