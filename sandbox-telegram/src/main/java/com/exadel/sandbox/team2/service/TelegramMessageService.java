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
            case "INFO" -> telegramState = TelegramState.GET_USER_BOOKINGS;
            case "\uD83D\uDDD2 Menu" -> telegramState = TelegramState.MENU;
            case "\uD83D\uDC64 Account" -> telegramState = TelegramState.GET_ACCOUNT_INFO;
            case "\uD83D\uDCD8 Contact" -> telegramState = TelegramState.GET_CONTACT;
            case "⚙️ Settings" -> telegramState = TelegramState.SETTINGS;
            case "One day" -> telegramState = TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED;
            case "Continuous" -> telegramState = TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED;
            case "Recurring" -> telegramState = TelegramState.RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED;
            case "LANGUAGE" -> telegramState = TelegramState.CHOOSE_LANGUAGE;
            case "CANCEL_BOOKING" -> telegramState = TelegramState.CANCEL_BOOKING;
            case "DEFINE_WORKPLACE_ATTRIBUTES" -> {
                if(user.getTelegramState() == TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = TelegramState.ONE_DAY_IS_KITCHEN_NEED;
                }else if(user.getTelegramState() == TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = TelegramState.CONTINUOUS_IS_KITCHEN_NEED;
                }else{
                    telegramState = TelegramState.RECURRING_IS_KITCHEN_NEED;
                }
            }
            case "NOT_DEFINE_WORKPLACE_ATTRIBUTES" ->{
                if(user.getTelegramState() == TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = TelegramState.ONE_DAY_SELECT_DATE;
                }else if(user.getTelegramState() == TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = TelegramState.CONTINUOUS_SELECT_DATE;
                }else{
                    telegramState = TelegramState.RECURRING_SELECT_WEEK_DAY;
                }
            }
            case "REPORT" -> telegramState = TelegramState.REPORT;
        }
        if(telegramState == null){
            switch (user.getTelegramState()){
                case CHOOSE_COUNTRY -> telegramState = TelegramState.CHOOSE_CITY;
                case CHOOSE_CITY -> telegramState = TelegramState.ASSIGN_BOOKING_TYPE;
                case ONE_DAY_IS_KITCHEN_NEED, CONTINUOUS_IS_KITCHEN_NEED, RECURRING_IS_KITCHEN_NEED -> telegramState = TelegramState.IS_CONFERENCE_HALL_NEED;
                case IS_CONFERENCE_HALL_NEED -> telegramState = TelegramState.IS_NEXT_TO_WINDOWS_NEED_BE;
                case IS_NEXT_TO_WINDOWS_NEED_BE -> telegramState = TelegramState.IS_PC_NEED_BE;
                case IS_PC_NEED_BE -> telegramState = TelegramState.IS_MONITOR_NEED_BE;
                case IS_MONITOR_NEED_BE -> telegramState = TelegramState.IS_KEYBOARD_NEED_BE;
                case IS_KEYBOARD_NEED_BE -> telegramState = TelegramState.IS_MOUSE_NEED_BE;
                case IS_MOUSE_NEED_BE -> telegramState = TelegramState.IS_HEADSET_NEED_BE;
                case IS_HEADSET_NEED_BE -> telegramState = TelegramState.FINISH_DEFINE_WORKPLACE_ATTRIBUTES;
                case ONE_DAY_SELECT_DATE -> telegramState = TelegramState.SHOW_OFFICES_BY_CITY;
                case CONTINUOUS_SELECT_DATE -> telegramState = TelegramState.SELECT_END_DATE;
                case RECURRING_SELECT_WEEK_DAY -> telegramState = TelegramState.RECURRING_DEFINE_WEEKDAYS;
                case RECURRING_DEFINE_WEEKDAYS -> telegramState = TelegramState.RECURRING_DEFINE_WEEKS;
                case RECURRING_DEFINE_WEEKS -> telegramState = TelegramState.RECURRING_ASSIGN_START_WEEKDAY;
                case RECURRING_ASSIGN_START_WEEKDAY -> telegramState = TelegramState.RECURRING_ASSIGN_END_WEEKDAY;
                case RECURRING_ASSIGN_END_WEEKDAY -> telegramState = TelegramState.RECURRING_SHOW_WORKPLACES;
                case SELECT_END_DATE -> telegramState = TelegramState.SHOW_OFFICES_CONTINUOUS;
                case SHOW_OFFICES_BY_CITY -> telegramState = TelegramState.SHOW_WORKPLACES_BY_OFFICE;
                case SHOW_OFFICES_CONTINUOUS -> telegramState = TelegramState.SHOW_WORKPLACES_CONTINUOUS;
                case SHOW_WORKPLACES_BY_OFFICE, SHOW_WORKPLACES_CONTINUOUS, RECURRING_SHOW_WORKPLACES -> telegramState = TelegramState.BOOK_ONE_DAY_WORKPLACE;
                case CHOOSE_LANGUAGE -> telegramState = TelegramState.SET_LANGUAGE;
                case CANCEL_BOOKING -> telegramState = TelegramState.DELETE_USER_BOOKING;
            }
        }
        if(telegramState != null)
            user.setTelegramState(telegramState);

        return isEditMessage;
    }

    private void backAndUserState(User user){
        TelegramState telegramState;
        telegramState = switch (user.getTelegramState()){
            case MENU,GET_ACCOUNT_INFO,GET_CONTACT,SETTINGS -> TelegramState.MAIN_MENU;
            case CHOOSE_COUNTRY,GET_USER_BOOKINGS,BOOK_ONE_DAY_WORKPLACE,DELETE_USER_BOOKING -> TelegramState.MENU;
            case CHOOSE_CITY -> TelegramState.CHOOSE_COUNTRY;
            case ASSIGN_BOOKING_TYPE -> TelegramState.CHOOSE_CITY;
            case ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED, CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED, RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED -> TelegramState.ASSIGN_BOOKING_TYPE;
            case ONE_DAY_IS_KITCHEN_NEED -> TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED;
            case CONTINUOUS_IS_KITCHEN_NEED -> TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED;
            case RECURRING_IS_KITCHEN_NEED -> TelegramState.RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED;
            case IS_CONFERENCE_HALL_NEED -> TelegramState.BACK_TO_IS_KITCHEN_NEED;
            case IS_NEXT_TO_WINDOWS_NEED_BE -> TelegramState.IS_CONFERENCE_HALL_NEED;
            case IS_PC_NEED_BE -> TelegramState.IS_NEXT_TO_WINDOWS_NEED_BE;
            case IS_MONITOR_NEED_BE -> TelegramState.IS_PC_NEED_BE;
            case IS_KEYBOARD_NEED_BE -> TelegramState.IS_MONITOR_NEED_BE;
            case IS_MOUSE_NEED_BE -> TelegramState.IS_KEYBOARD_NEED_BE;
            case IS_HEADSET_NEED_BE -> TelegramState.IS_MOUSE_NEED_BE;
            case ONE_DAY_SELECT_DATE -> TelegramState.BACK_FROM_SELECT_ONE_DAY_DATE;
            case CONTINUOUS_SELECT_DATE -> TelegramState.BACK_FROM_SELECT_CONTINUOUS_DATE;
            case RECURRING_SELECT_WEEK_DAY -> TelegramState.BACK_FROM_SELECT_RECURRING_DATE;
            case SHOW_OFFICES_BY_CITY -> TelegramState.ONE_DAY_SELECT_DATE;
            case SHOW_WORKPLACES_BY_OFFICE -> TelegramState.SHOW_OFFICES_BY_CITY;
            case SELECT_END_DATE -> TelegramState.CONTINUOUS_SELECT_DATE;
            case SHOW_OFFICES_CONTINUOUS -> TelegramState.SELECT_END_DATE;
            case SHOW_WORKPLACES_CONTINUOUS -> TelegramState.SHOW_OFFICES_CONTINUOUS;
            case RECURRING_DEFINE_WEEKDAYS -> TelegramState.RECURRING_SELECT_WEEK_DAY;
            case RECURRING_DEFINE_WEEKS -> TelegramState.RECURRING_DEFINE_WEEKDAYS;
            case RECURRING_ASSIGN_START_WEEKDAY -> TelegramState.RECURRING_DEFINE_WEEKS;
            case RECURRING_ASSIGN_END_WEEKDAY -> TelegramState.RECURRING_ASSIGN_START_WEEKDAY;
            case RECURRING_SHOW_WORKPLACES -> TelegramState.RECURRING_ASSIGN_END_WEEKDAY;
            default -> null;
        };
        if(telegramState != null)
            user.setTelegramState(telegramState);
    }
}