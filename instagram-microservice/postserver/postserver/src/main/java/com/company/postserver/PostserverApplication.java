package com.company.postserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PostserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostserverApplication.class, args);
	}

}
