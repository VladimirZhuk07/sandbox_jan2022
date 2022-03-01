package com.exadel.sandbox.team2.service;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class TelegramMessageService {

    TelegramAuthorizationService authorizationService;

    public SendMessage handleUpdate(Update update) {

        if(!update.hasMessage()){
            return null;
        }

        String chatId=update.getMessage().getChatId().toString();
        Optional<User> current = authorizationService.findActiveUserByChatId(chatId);

        if(current.isEmpty()) {
            if (update.getMessage().hasText()) {
                String command = update.getMessage().getText().trim();

                if (command.equals("/start")) {
                    return requestPhoneNumber(chatId, "Please send your phone number for invitation!");
                }
                else if (command.startsWith("/start")) {
                    String authorizationCode = command.substring("/start".length()).trim();
                    var response = authorizationService.authenticate(chatId, authorizationCode);
                    if(response.isPresent()){
                      return requestPhoneNumber(chatId, "Please send your phone number for authentication");
                    }
                    return SendMessage.builder()
                            .chatId(chatId)
                            .text("Sorry you are not use this bot")
                            .build();
                }
            }
            else {
                Contact contact = update.getMessage().getContact();
                var response = authorizationService.authenticatePhoneNumber(chatId, contact.getPhoneNumber());
                if(response.isPresent()){
                    User user = response.get();
                    switch (user.getStatus()){
                        case NEW : return SendMessage.builder()
                                .chatId(chatId)
                                .text("We sent invitation your email")
                                .build();
                        case ACTIVE: return SendMessage.builder()
                                .chatId(chatId)
                                .text("Please select your function")
                                .replyMarkup(createReplyKeyboardMarkup(new String[][]{{"\uD83C\uDF0E Language","⚙️ Settings"},{"Booking","History"}}))
                                .build();
                    }
                }

            }
        }
        if(update.hasCallbackQuery()){
            return SendMessage.builder()
                              .chatId(update.getMessage()
                                            .getChatId()
                                            .toString())
                              .text("Please select command")
                              .build();
        }

        return SendMessage.builder()
                .chatId(chatId)
                .text("Empty")
                .build();

    }


    public ReplyKeyboardMarkup createReplyKeyboardMarkup(String [][] titles){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> rows=new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            List<KeyboardButton> buttons=new ArrayList();
            for (int j = 0; j < titles[i].length; j++) {
                buttons.add(createReplyButton(titles[i][j],false));
            }
            rows.add(new KeyboardRow(buttons));
        }
        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    private SendMessage requestPhoneNumber(String chatId,String message){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        replyKeyboardMarkup.setKeyboard(List.of(new KeyboardRow(List.of(createReplyButton("Share your number >",true)))));

        return SendMessage.builder()
                            .chatId(chatId)
                            .text(message)
                            .replyMarkup(replyKeyboardMarkup)
                            .build();
    }

    private KeyboardButton createReplyButton(String buttonText, boolean requestContact){
        return KeyboardButton.builder()
                                .text(buttonText)
                                .requestContact(requestContact)
                             .build();
    }
}