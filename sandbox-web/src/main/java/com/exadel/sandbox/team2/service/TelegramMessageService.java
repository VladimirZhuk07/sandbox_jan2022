package com.exadel.sandbox.team2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class TelegramMessageService {

    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage=new SendMessage();
        String name = update.getMessage().getChat().getFirstName();
        String text=update.getMessage().getText();
        replyMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        replyMessage.setText(name+", Do you mean -> "+text+"?(Webhook)");
        return replyMessage;
    }

}
