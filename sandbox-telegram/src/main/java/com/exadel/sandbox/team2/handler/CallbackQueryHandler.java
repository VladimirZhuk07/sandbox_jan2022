package com.exadel.sandbox.team2.handler;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.handler.base.BaseHandler;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.serivce.service.UserService;
import com.exadel.sandbox.team2.service.service.TelegramService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class CallbackQueryHandler implements BaseHandler {

  private final UserService userService;
  private final TelegramService telegramService;
  private  final TelegramUtils utils;

  @Override
  public SendMessage handleSendMessage(Update update, User user) {
    String chatId = utils.getChatId(update);
    SendMessage sendMessage;

    switch (user.getTelegramState()){
      case MAIN_MENU -> sendMessage = utils.getSendMessage(chatId, "Please select your function", new String[][]{{"\uD83D\uDDD2 Menu","\uD83D\uDC64 Account"}, {"\uD83D\uDCD8 Contact",  "⚙️ Settings"}});
      case UPDATE_PHONE_NUMBER -> sendMessage = utils.getSendMessage(chatId, "Enter your new number", "Please send your number!");
      case CHOOSE_COUNTRY -> {
        String data = update.getCallbackQuery().getData();
        sendMessage = telegramService.getCountries(chatId, "Please select country", data);
      }
      case CHOOSE_CITY -> {
        String data = update.getCallbackQuery().getData();
        sendMessage = telegramService.getCities(chatId, "Please select city", data);
      }
      case ASSIGN_BOOKING_TYPE -> {
        String data = update.getCallbackQuery().getData();
        sendMessage = telegramService.setBookingType(chatId, "Please, select booking type", new String[][] {{"One day"},{"Continuous"},{"Recurring"}}, new String[][] {{"One day"},{"Continuous"},{"Recurring"}}, data);
      }
      case ONE_DAY_SELECT_DATE, CONTINUOUS_SELECT_DATE -> sendMessage = utils.getSendMessage(chatId, "Please write date in form of `2020-01-08`");
      default -> sendMessage = utils.getSendMessage(chatId, "Command not found");
    }
    userService.save(user);
    return sendMessage;
  }

  @Override
  public EditMessageText handleEditMessage(Update update, User user) {
//    String chatId = utils.getChatId(update);
//    int messageId = update.getCallbackQuery().getMessage().getMessageId();
//    EditMessageText editMessageText = null;
//
//    switch (user.getTelegramState()){
//
//    }
//
//    userService.save(user);
//    return editMessageText;
    return null;
  }
}
