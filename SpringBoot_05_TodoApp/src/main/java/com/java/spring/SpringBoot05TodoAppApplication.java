package com.java.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.java")
public class SpringBoot05TodoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot05TodoAppApplication.class, args);
	}

}
