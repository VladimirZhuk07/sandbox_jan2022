package com.exadel.sandbox.team2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BeanConfiguration {

  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
}
