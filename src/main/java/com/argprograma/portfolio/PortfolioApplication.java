package com.argprograma.portfolio;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class PortfolioApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		new File(System.getProperty("user.dir")+ "/proyectos-upload-img/").mkdir();
		new File(System.getProperty("user.dir")+ "/persona-upload-img/").mkdir();
		new File(System.getProperty("user.dir")+ "/habilidades-profile-upload-img/").mkdir();
		SpringApplication.run(PortfolioApplication.class, args);
	}

}
