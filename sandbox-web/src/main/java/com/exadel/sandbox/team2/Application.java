package com.exadel.sandbox.team2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

   @RequestMapping("/")
   public String home() {
      return "Hello Docker World";
   }
}
