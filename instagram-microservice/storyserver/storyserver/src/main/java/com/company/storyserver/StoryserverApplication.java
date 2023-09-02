package com.company.storyserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StoryserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoryserverApplication.class, args);
	}

}
