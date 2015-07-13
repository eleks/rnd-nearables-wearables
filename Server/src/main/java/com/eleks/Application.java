package com.eleks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.eleks.repository")
@EntityScan(basePackages = { "com.eleks.model.db", "com.eleks.model.teampro" })
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

// @SpringBootApplication
// public class Application extends SpringBootServletInitializer {
//
// @Override
// protected SpringApplicationBuilder configure(SpringApplicationBuilder
// application) {
// return application.sources(Application.class);
// }
//
// public static void main(String[] args) throws Exception {
// SpringApplication.run(Application.class, args);
// }
//
// }