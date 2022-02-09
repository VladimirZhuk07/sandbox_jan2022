package com.exadel.sandbox.team2.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.exadel.sandbox")
public class Config {

    @Autowired
    private Environment env;

    @Bean
    public String botUsername() {
        return env.getProperty("telegramBot.botUsername");
    }

    @Bean
    public String botToken() {
        return env.getProperty("telegramBot.botToken");
    }

    @Bean
    public String botPath() {
        return env.getProperty("telegramBot.botPath");
    }
}
