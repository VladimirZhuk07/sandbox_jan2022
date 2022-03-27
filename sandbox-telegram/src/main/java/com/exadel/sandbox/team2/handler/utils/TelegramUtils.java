package com.exadel.sandbox.team2.handler.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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

  public  SendMessage getSendMessage(String chatId, String message){
    return SendMessage.builder()
                        .chatId(chatId)
                        .text(message)
                      .build();
  }

  public   SendMessage getSendMessage(String chatId, String message, String  title){
    return requestPhoneNumber(chatId, message,title);
  }

  public   SendMessage getSendMessage(String chatId, String message, String [][] titles){
    SendMessage sendMessage = getSendMessage(chatId,message);
    sendMessage.setReplyMarkup(createReplyKeyboardMarkup(titles));
    return sendMessage;
  }

  public   SendMessage getSendMessage(String chatId, String message, String [][] titles, String [][] commands ){
    SendMessage sendMessage = getSendMessage(chatId,message);
    sendMessage.setReplyMarkup(createInlineKeyboardMarkup(titles,commands));
    return sendMessage;
  }

  public EditMessageText getEditMessage(String chatId, Integer messageId, String message){
    return EditMessageText.builder()
            .chatId(chatId)
            .messageId(messageId)
            .text(message).build();
  }

  public EditMessageText getEditMessage(String chatId, int messageId, String message, String [][] titles, String [][] commands){
    EditMessageText editMessage = getEditMessage(chatId, messageId, message);
    editMessage.setReplyMarkup(createInlineKeyboardMarkup(titles, commands));
    return editMessage;
  }

  public SendMessage showOptions(String chatId, String message, List<List<String>> rows){
    SendMessage sendMessage = getSendMessage(chatId, message);
    InlineKeyboardMarkup markup = createMarkup(rows);
    sendMessage.setReplyMarkup(markup);
    return sendMessage;
  }

  private InlineKeyboardMarkup  createMarkup(List<List<String>> rows){
    List<List<InlineKeyboardButton>> rowList=new ArrayList<>();
    for (List<String> row : rows) {
      List<InlineKeyboardButton> dRow=new ArrayList<>();
      for (String word : row) {
        InlineKeyboardButton button;
        button=new InlineKeyboardButton(word);
        button.setCallbackData(word);
        dRow.add(button);
      }
      rowList.add(dRow);
    }

    return new InlineKeyboardMarkup(rowList);
  }

  private  SendMessage requestPhoneNumber(String chatId, String message, String title){

    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setOneTimeKeyboard(true);

    replyKeyboardMarkup.setKeyboard(List.of(new KeyboardRow(List.of(createReplyButton(title,true)))));

    SendMessage sendMessage = getSendMessage(chatId, message);
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
    for (String[] title : titles) {
      List<KeyboardButton> buttons = new ArrayList<>();
      for (String s : title) {
        buttons.add(createReplyButton(s, false));
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
