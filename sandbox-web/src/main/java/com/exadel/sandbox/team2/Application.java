package com.exadel.sandbox.team2;

import com.exadel.sandbox.team2.component.TelegramBotComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class Application {

   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

   @Bean
   public TelegramBotComponent getTelegramBotComponent(){
      return new TelegramBotComponent();
   }
}
