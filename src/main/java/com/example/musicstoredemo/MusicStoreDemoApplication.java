package com.example.musicstoredemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MusicStoreDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicStoreDemoApplication.class, args);
	}

}
