package com.yejo.interior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YejoApplication {

	public static void main(String[] args) {
		SpringApplication.run(YejoApplication.class, args);
	}

}
