package com.exadel.sandbox.team2.telegram.longpolling.component;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBotComponent extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private static final String USERNAME = "exadelofficebot";
    @Value("${bot.token}")
    private static final String TOKEN = "5294755437:AAEqe6YcFIUExNjVGBG56R2ypK1cLKf8o14";

    public TelegramBotComponent() {

    }

    public TelegramBotComponent(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
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
