package com.exadel.sandbox.team2.service;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.handler.CallbackQueryHandler;
import com.exadel.sandbox.team2.handler.MessageHandler;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
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
    LocaleMessageService lms;

    public BotApiMethod<?> handleUpdate(Update update) {

        String chatId=utils.getChatId(update);
        Optional<User> current = authorizationService.findActiveUserByChatId(chatId);

        if(current.isPresent()){
            User user = current.get();
            String command = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getData();
            boolean isEditMessage = false;
            if(command.equals("Back") || command.equals("⬅ ️Back")) {
                TelegramState.backAndUserState(user);
            }else
                isEditMessage = TelegramState.commandToTelegramState(command, user);
            if(!isEditMessage)
                return update.hasCallbackQuery() ? callbackQueryHandler.handleSendMessage(update, user) : messageHandler.handleSendMessage(update, user);
            return update.hasCallbackQuery() ? callbackQueryHandler.handleEditMessage(update, user) : messageHandler.handleEditMessage(update, user);
        }

        if(!update.hasMessage()){
            return beforeGetContact(chatId, "/start");
        }

        return update.getMessage().hasText() ?
               beforeGetContact(chatId,update.getMessage().getText()) :
               afterGetContact(chatId, update.getMessage().getContact());
    }

    private SendMessage beforeGetContact(String chatId, String command){
        String authorizationCode = command.substring("/start".length()).trim();
        if (!authorizationCode.isBlank()) {
            return  authorizationService.authenticate(chatId, authorizationCode)
              .map(user ->
                utils.getSendMessage(chatId, lms.getMessage("reply.askPhoneForAuthentication"), lms.getMessage("reply.askToSharePhoneNumber")))
              .orElse(utils.getSendMessage(chatId, lms.getMessage("error.youAreNotAbleToUseThisBot")));
        }
        return utils.getSendMessage(chatId, lms.getMessage("reply.askPhoneForInvitation"), lms.getMessage("reply.askToSharePhoneNumber"));
    }

    private SendMessage afterGetContact(String chatId, Contact contact){
        return  authorizationService.authenticatePhoneNumber(chatId, contact.getPhoneNumber())
          .map(user -> user.getStatus() == UserState.NEW?
            utils.getSendMessage(chatId, lms.getMessage("reply.weSentInvitationToYourEmail")) :
            utils.getSendMessage(chatId, "Please select your function",
                    new String[][]{{"\uD83D\uDDD2 Menu", "\uD83D\uDC64 Account"},
                            {"\uD83D\uDCD8 Contact", "⚙️ Settings"}}))
          .orElse(utils.getSendMessage(chatId, "Sorry, but your account is blocked or not exist"));
    }
}