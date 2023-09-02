package com.company.commentserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class CommentserverApplication{
	public static void main(String[] args) {
		SpringApplication.run(CommentserverApplication.class, args);
	}

}
