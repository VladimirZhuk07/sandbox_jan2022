package com.exadel.sandbox.team2.handler.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class TelegramUtils {

  public  String getChatId(Update update){
    if(update.hasCallbackQuery()){
      return update.getCallbackQuery().getMessage().getChatId().toString();
    }
    return update.getMessage().getChatId().toString();
  }

  public  SendMessage getMessage(String chatId, String message){
    return SendMessage.builder()
                        .chatId(chatId)
                        .text(message)
                      .build();
  }

  public   SendMessage getMessage(String chatId, String message, String  title){
    return requestPhoneNumber(chatId, message,title);
  }

  public   SendMessage getMessage(String chatId, String message, String [][] titles){
    SendMessage sendMessage = getMessage(chatId,message);
    sendMessage.setReplyMarkup(createReplyKeyboardMarkup(titles));
    return sendMessage;
  }

  public   SendMessage getMessage(String chatId, String message, String [][] titles, String [][] commands ){
    SendMessage sendMessage = getMessage(chatId,message);
    sendMessage.setReplyMarkup(createInlineKeyboardMarkup(titles,commands));
    return sendMessage;
  }

  private  SendMessage requestPhoneNumber(String chatId, String message, String title){

    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setOneTimeKeyboard(true);

    replyKeyboardMarkup.setKeyboard(List.of(new KeyboardRow(List.of(createReplyButton(title,true)))));

    SendMessage sendMessage = getMessage(chatId, message);
    sendMessage.setReplyMarkup(replyKeyboardMarkup);

    return sendMessage;
  }

  private  InlineKeyboardMarkup createInlineKeyboardMarkup(String [][] titles, String[][] commands) {
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rows = new ArrayList<>();

    for (int i = 0; i < titles.length; i++) {
      List<InlineKeyboardButton> row = new ArrayList<>();
      for (int j = 0; j < titles[i].length; j++) {
        row.add(createInlineButton(titles[i][j],commands[i][j]));
      }
      rows.add(row);
    }
    inlineKeyboardMarkup.setKeyboard(rows);
    return inlineKeyboardMarkup;
  }

  private  ReplyKeyboardMarkup createReplyKeyboardMarkup(String [][] titles){
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setOneTimeKeyboard(true);
    List<KeyboardRow> rows=new ArrayList<>();
    for (int i = 0; i < titles.length; i++) {
      List<KeyboardButton> buttons=new ArrayList<>();
      for (int j = 0; j < titles[i].length; j++) {
        buttons.add(createReplyButton(titles[i][j],false));
      }
      rows.add(new KeyboardRow(buttons));
    }
    replyKeyboardMarkup.setKeyboard(rows);
    return replyKeyboardMarkup;
  }

  private  InlineKeyboardButton createInlineButton(String buttonText, String command){
    return InlineKeyboardButton.builder()
      .text(buttonText)
      .callbackData(command)
      .build();
  }

  private  KeyboardButton createReplyButton(String buttonText, boolean requestContact){
    return KeyboardButton.builder()
      .text(buttonText)
      .requestContact(requestContact)
      .build();
  }

}
