package com.example.fireAuth_REST_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class FireAuthRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FireAuthRestApiApplication.class, args);
	}

}
