package com.exadel.sandbox.team2.telegram.longpolling.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//@Component
public class TelegramLongPollingBotComponent extends TelegramLongPollingBot {

    private final String username;
    private final String token;

    //@Autowired
    public TelegramLongPollingBotComponent(@Value("${telegramBot.username}") String username,
                                           @Value("${telegramBot.token}") String token) {
        this.username = username;
        this.token = token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (null != update.getMessage() && update.getMessage().hasText()) {
            long chat_id = update.getMessage().getChatId();

            try {
                SendMessage message = new SendMessage();
                String name = update.getMessage().getChat().getFirstName();
                String text = update.getMessage().getText();
                message.setChatId(String.valueOf(chat_id));
                message.setText(name + ", Do you mean -> " + text + "?");
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
