package com.exadel.sandbox.team2.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
public class TelegramBotComponent extends TelegramLongPollingBot {
  @Value("${telegrambot.botUsername}")
  private String botUsername;
  @Value("${telegrambot.botToken}")
  private String botToken;

  @Override
  public void onUpdateReceived(Update update) {
    try {
      SendMessage message=new SendMessage();
      String name = update.getMessage().getChat().getFirstName();
      String text=update.getMessage().getText();
      message.setChatId(String.valueOf(update.getMessage().getChatId()));
      message.setText(name+", Do you mean -> "+text+"?");
      execute(message);
    } catch (TelegramApiException e) {
       e.printStackTrace();
    }
  }
}
