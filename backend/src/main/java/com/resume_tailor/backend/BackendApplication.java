package com.resume_tailor.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@CrossOrigin(origins = "http://localhost:4200")
public class BackendApplication {
	@RequestMapping("/")
	public String TestApp(){
		return "Hello World!";
	}
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
