package com.henriquesbraga.springbootbasicjwt.config;

import com.henriquesbraga.springbootbasicjwt.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

  @Autowired
  private DBService dbService;

  @Bean
  public boolean instantiateDatabase() throws ParseException {
    dbService.instantiateTesteDatabase();
    return true;
  }
}
