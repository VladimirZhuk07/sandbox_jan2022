package com.exadel.sandbox.team2.service;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.handler.CallbackQueryHandler;
import com.exadel.sandbox.team2.handler.MessageHandler;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class TelegramMessageService {

    TelegramAuthorizationService authorizationService;
    CallbackQueryHandler callbackQueryHandler;
    MessageHandler messageHandler;
    TelegramUtils utils;

    public SendMessage handleUpdate(Update update) {

        String chatId=utils.getChatId(update);
        Optional<User> current = authorizationService.findActiveUserByChatId(chatId);

        if(current.isPresent()){
            return update.hasCallbackQuery() ? callbackQueryHandler.handle(update, current.get()) : messageHandler.handle(update, current.get());
        }

        return update.getMessage().hasText() ?
               beforeGetContact(chatId,update.getMessage().getText().trim()) :
               afterGetContact(chatId, update.getMessage().getContact());
    }

    private SendMessage beforeGetContact(String chatId, String command){
        String authorizationCode = command.substring("/start".length()).trim();
        if (!authorizationCode.isBlank()) {
            return  authorizationService.authenticate(chatId, authorizationCode)
              .map(user ->
                utils.getMessage(chatId, "Please send your phone number for authentication","\uD83D\uDCF2 Share Phone number"))
              .orElse(utils.getMessage(chatId,"Sorry you are not use this bot"));
        }
        return utils.getMessage(chatId, "Please send your phone number for invitation!", "\uD83D\uDCF2 Share Phone number");
    }

    private SendMessage afterGetContact(String chatId, Contact contact){
        return  authorizationService.authenticatePhoneNumber(chatId, contact.getPhoneNumber())
          .map(user -> user.getStatus() == UserState.NEW?
            utils.getMessage(chatId,"We sent invitation your email") :
            utils.getMessage(chatId, "Please select your function", new String[][]{{"\uD83D\uDDD2 Menu","\uD83D\uDC64 Account"}, {"\uD83D\uDCD8 Contact",  "⚙️ Settings"}}))
          .orElse(null);
    }
}