package com.cfl.cfl_project;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableCaching
public class CflProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CflProjectApplication.class, args);

	}

}
