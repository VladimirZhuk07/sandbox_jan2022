package com.exadel.sandbox.team2.telegram.service;

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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class TelegramMessageService {

    TelegramAuthorizationService authorizationService;

    public SendMessage handleUpdate(Update update) {
        String chatId=update.getMessage().getChatId().toString();
        if(update.hasCallbackQuery()){
            return SendMessage.builder()
                              .chatId(update.getMessage()
                                            .getChatId()
                                            .toString())
                              .text("Please select command")
                              .build();
        }
        else if(update.hasMessage() && !update.getMessage().hasText()){
            Contact contact=update.getMessage().getContact();
            var response = authorizationService.saveTelegramUserPhone(chatId, contact.getPhoneNumber());

            if(response.getCode() == 200){


                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setSelective(true);
                replyKeyboardMarkup.setResizeKeyboard(true);
                replyKeyboardMarkup.setOneTimeKeyboard(true);
                // просто хочу показать как кнопка надо создать дальше вы сами будете
                replyKeyboardMarkup.setKeyboard(List.of(new KeyboardRow(List.of(
                        createReplyButton("Book place >",false),
                        createReplyButton("Show my booking >",false),
                        createReplyButton("Settings >",false)
                ))));

                       SendMessage buttons= SendMessage.builder()
                               .chatId(chatId)
                               .text("Select action")
                               .replyMarkup(replyKeyboardMarkup)
                               .build();
                       return buttons;

            }

            if(response.getCode() == 201){


            }
            return SendMessage.builder()
                              .chatId(update.getMessage()
                                            .getChatId()
                                            .toString())
                              .text("Thanks for your contact: ".concat(response.getMessage()))
                              .build();
        }
        else if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText().trim();

            if(command.equals("/start")){
                return requestPhoneNumber(chatId, "Please send your phone number");
            }
            else if(command.startsWith("/start")){
                String authorizationCode = command.substring("/start".length()).trim();
                var response= authorizationService.authorizationTelegramUser(authorizationCode,chatId);
                if(response.getCode() == 200){
                    return requestPhoneNumber(chatId,"Please send your phone number "+ authorizationCode);
                }

                return  SendMessage
                  .builder()
                  .chatId(chatId)
                  .text("You are not Exadel member so you can not use this bot")
                  .build();

            }
            return SendMessage
                            .builder()
                            .chatId(chatId)
                            .text("Please select command")
                            .build();
        }
        return null;
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