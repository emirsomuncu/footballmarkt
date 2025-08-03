package com.somuncu.footballmarkt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FootballmarktApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballmarktApplication.class, args);
	}

}
