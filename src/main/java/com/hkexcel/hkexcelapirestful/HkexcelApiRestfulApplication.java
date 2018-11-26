package com.hkexcel.hkexcelapirestful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan("com.hkexcel.hkexcelapirestful")
@EnableConfigurationProperties
public class HkexcelApiRestfulApplication{

	public static void main(String[] args) {
		SpringApplication.run(HkexcelApiRestfulApplication.class, args);
		 
	}
	
	 
}
