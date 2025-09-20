package com.mymicroservice.accounts;

import com.mymicroservice.accounts.dto.AccountContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "AuditAwareImpl")
@EnableFeignClients
@EnableConfigurationProperties(value = {AccountContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservice Rest API Documentation",
				description = "Bank Accounts microservice Rest API Documentation",
				version = "v1",
				contact = @Contact(
						name = "MD ASIF SHAMIM",
						email = "asifshamim0403@gmail.com",
						url = "https://linkedin.com/in/md-asif-shamim/"
				),
				license =@License(
						name = "Apache 2.0",
						url = "https://tomcat.apache.org/"
				)
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
