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
            if(command.equals("Back")) {
                backAndUserState(user);
            }else
                isEditMessage = commandToTelegramState(command, user);
            if(!isEditMessage)
                return update.hasCallbackQuery() ? callbackQueryHandler.handleSendMessage(update, user) : messageHandler.handleSendMessage(update, user);
            return update.hasCallbackQuery() ? callbackQueryHandler.handleEditMessage(update, user) : messageHandler.handleEditMessage(update, user);
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
                utils.getSendMessage(chatId, lms.getMessage("reply.askPhoneForAuthentication"), lms.getMessage("reply.askToSharePhoneNumber")))
              .orElse(utils.getSendMessage(chatId, lms.getMessage("error.youAreNotAbleToUseThisBot")));
        }
        return utils.getSendMessage(chatId, lms.getMessage("reply.askPhoneForInvitation"), lms.getMessage("reply.askToSharePhoneNumber"));
    }

    private SendMessage afterGetContact(String chatId, Contact contact){
        return  authorizationService.authenticatePhoneNumber(chatId, contact.getPhoneNumber())
          .map(user -> user.getStatus() == UserState.NEW?
            utils.getSendMessage(chatId, lms.getMessage("reply.weSentInvitationToYourEmail")) :
            utils.getSendMessage(chatId, "Please select your function", new String[][]{{lms.getMessage("menu.menu"), lms.getMessage("menu.account")},
                    {lms.getMessage("menu.contact"), lms.getMessage("menu.settings")}},
                    new String[][]{{"\uD83D\uDDD2 Menu", "\uD83D\uDC64 Account"},
                            {"\uD83D\uDCD8 Contact", "⚙️ Settings"}}))
          .orElse(null);
    }

    private boolean commandToTelegramState(String command, User user){
        boolean isEditMessage = false;
        TelegramState telegramState = null;
        switch (command){
            case "/start" -> telegramState = TelegramState.MAIN_MENU;
            case "PHONE" -> telegramState = TelegramState.UPDATE_PHONE_NUMBER;
            case "BOOK" -> telegramState = TelegramState.CHOOSE_COUNTRY;
            case "\uD83D\uDDD2 Menu" -> telegramState = TelegramState.MENU;
            case "\uD83D\uDC64 Account" -> telegramState = TelegramState.GET_ACCOUNT_INFO;
            case "\uD83D\uDCD8 Contact" -> telegramState = TelegramState.GET_CONTACT;
            case "⚙️ Settings" -> telegramState = TelegramState.SETTINGS;
            case "One day" -> telegramState = TelegramState.ONE_DAY_SELECT_DATE;
            case "Continuous" -> telegramState = TelegramState.CONTINUOUS_SELECT_DATE;
            case "Recurring" -> telegramState = TelegramState.RECURRING_SELECT_WEEK_DAY;
            case "LANGUAGE" -> telegramState = TelegramState.CHOOSE_LANGUAGE;
        }
        if(telegramState == null){
            switch (user.getTelegramState()){
                case CHOOSE_COUNTRY -> telegramState = TelegramState.CHOOSE_CITY;
                case CHOOSE_CITY -> telegramState = TelegramState.ASSIGN_BOOKING_TYPE;
                case ONE_DAY_SELECT_DATE -> telegramState = TelegramState.SHOW_OFFICES_BY_CITY;
                case SHOW_OFFICES_BY_CITY -> telegramState = TelegramState.SHOW_WORKPLACES_BY_OFFICE;
                case SHOW_WORKPLACES_BY_OFFICE -> telegramState = TelegramState.BOOK_ONE_DAY_WORKPLACE;
                case CHOOSE_LANGUAGE -> telegramState = TelegramState.SET_LANGUAGE;
            }
        }
        if(telegramState != null)
            user.setTelegramState(telegramState);

        return isEditMessage;
    }

    private void backAndUserState(User user){
        TelegramState telegramState;
        telegramState = switch (user.getTelegramState()){
            case ASSIGN_BOOKING_TYPE -> TelegramState.BACK_TO_MENU;
            case MENU,GET_ACCOUNT_INFO,GET_CONTACT,SETTINGS -> TelegramState.MAIN_MENU;
            default -> null;
        };
        user.setTelegramState(telegramState);
    }
}