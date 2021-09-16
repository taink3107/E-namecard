package com.example.redissession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class RedisSessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisSessionApplication.class, args);
	}

}
