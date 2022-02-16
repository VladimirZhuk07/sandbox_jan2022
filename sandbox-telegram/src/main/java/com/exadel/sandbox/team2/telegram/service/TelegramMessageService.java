package com.exadel.sandbox.team2.telegram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.List;

@Service
@Slf4j
public class TelegramMessageService {

    public SendMessage handleUpdate(Update update) {

        if(update.hasCallbackQuery()){
            return SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("Please select command").build();
        }
        else if(update.hasMessage() && !update.getMessage().hasText()){
            Contact contact=update.getMessage().getContact();
            return SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("Thanks for contact: ".concat(contact.getPhoneNumber())).build();
        }
        else if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId=update.getMessage().getChatId().toString();
            String command = update.getMessage().getText().trim();
            String userId = update.getMessage().getChat().getId().toString();

            if(command.equals("/start")){
                return requestPhoneNumber(chatId, "Please send your phone number");
            }
            else if(command.startsWith("/start")){
                String authorizationCode = command.substring("/start".length()).trim();


                return requestPhoneNumber(chatId,"Please send your phone number");
            }
            return SendMessage.builder().chatId(chatId).text("Please select command").build();
        }
        return null;
    }


    private SendMessage requestPhoneNumber(String chatId,String message){
        SendMessage sendMessage = new SendMessage();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);


        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Share your number >");
        keyboardButton.setRequestContact(true);
        replyKeyboardMarkup.setKeyboard(List.of(new KeyboardRow(List.of(keyboardButton))));

        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }
}