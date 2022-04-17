package com.argprograma.portfolio;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@SpringBootApplication
public class PortfolioApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/*@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("*").allowedHeaders("*");
			}
		};
	}*/

	public static void main(String[] args) {
		new File(System.getProperty("user.dir")+ "/proyectos-upload-img/").mkdir();
		new File(System.getProperty("user.dir")+ "/persona-upload-img/").mkdir();
		new File(System.getProperty("user.dir")+ "/habilidades-profile-upload-img/").mkdir();

		SpringApplication.run(PortfolioApplication.class, args);
	}
}