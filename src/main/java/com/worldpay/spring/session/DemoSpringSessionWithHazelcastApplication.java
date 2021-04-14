package com.worldpay.spring.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DemoSpringSessionWithHazelcastApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoSpringSessionWithHazelcastApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringSessionWithHazelcastApplication.class, args);
	}
}
