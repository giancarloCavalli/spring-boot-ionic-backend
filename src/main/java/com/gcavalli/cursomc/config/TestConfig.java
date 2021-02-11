package com.gcavalli.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.gcavalli.cursomc.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	DBService dbService;
	
	@Bean
	public void instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
	}
	
}
