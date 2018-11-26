package com.hkexcel.hkexcelapirestful.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;

import lombok.Getter;
import lombok.Setter;

@Component
//@Configuration("application.properties")
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb")
@EnableMongoRepositories(basePackages = "com.hkexcel.hkexcelapirestful.repository")
@Getter
@Setter
public class MongoConfig extends AbstractMongoConfiguration{
	
	@Value("${spring.data.mongodb.uri}")
	private String uri;
	
	@Value("${spring.data.mongodb.database}")
	private String database;
	
	@Value("${spring.data.mongodb.host}")
	private String host;
	
	@Value("${spring.data.mongodb.port}")
	private String port;
	
	@Override
	@Bean
	public MongoClient mongoClient() {
		System.out.println("uri: " + this.uri);
		System.out.println("database: " + this.database);
		System.out.println("host: " + this.host);
		System.out.println("port: " + this.port);
		//return new MongoClient(this.uri);
		return new MongoClient(this.host + ":" + this.port);
	}

	@Override
	protected String getDatabaseName() {
		//return "HKExcel";
		return this.database;
	}
	

}
