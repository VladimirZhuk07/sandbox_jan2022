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
public class MessageHandler implements BaseHandler {

  private final UserService userService;
  private final TelegramService telegramService;
  TelegramUtils utils;

  @Override
  public SendMessage handleSendMessage(Update update, User user) {
    String chatId = utils.getChatId(update);
    SendMessage sendMessage;

    switch (user.getTelegramState()) {
      case MAIN_MENU -> sendMessage = utils.getSendMessage(chatId, "Please select your function", new String[][]{{"\uD83D\uDDD2 Menu","\uD83D\uDC64 Account"}, {"\uD83D\uDCD8 Contact",  "⚙️ Settings"}});
      case GET_ACCOUNT_INFO -> sendMessage = utils.getSendMessage(chatId, String.format("First Name: %s\nLast Name: %s\nPhone Number: %s", user.getFirstName(), user.getLastName(), user.getPhoneNumber()), new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case GET_CONTACT -> sendMessage = utils.getSendMessage(chatId, "Contact: @nurmukhammadsunatullaev",  new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case MENU -> sendMessage = utils.getSendMessage(chatId, "Please select functionality", new String[][]{{"Booking", "Last Information"}, {"Cancel Booking", "Change Booking"}, {"Back"}}, new String[][]{{"BOOK", "INFO"}, {"CANCEL", "CHANGE"}, {"Back"}});
      case SETTINGS -> sendMessage = utils.getSendMessage(chatId, "Please select action", new String[][]{{"Change Phone Number", "Edit Account Information"}, {"Change Language", "Report"}, {"Back"}}, new String[][]{{"PHONE", "INFORMATION"}, {"LANGUAGE", "REPORT"}, {"Back"}});
      case SHOW_OFFICES_BY_CITY -> {
        String date = update.getMessage().getText();
        sendMessage = telegramService.getOfficesByCityForOneDay(chatId, "Please enter office id", date, user);
      }
      case SHOW_WORKPLACES_BY_OFFICE -> {
        String data = update.getMessage().getText();
        sendMessage = telegramService.getWorkplaceByMapId(chatId, "Please enter id to book workplace", data);
      }
      case BOOK_ONE_DAY_WORKPLACE -> {
        String data = update.getMessage().getText();
        sendMessage = telegramService.bookOneDayWorkplace(chatId, "Workplace is successfully booked", data, user);
      }
      default -> sendMessage = utils.getSendMessage(chatId, "Command not found");
    }
    userService.save(user);
    return sendMessage;
  }

  @Override
  public EditMessageText handleEditMessage(Update update, User user) {
//    String chatId = utils.getChatId(update);
//    int messageId = update.getMessage().getMessageId();
    EditMessageText messageText = null;
//    switch (user.getTelegramState()){
//      case MENU -> editMessageText = utils.getEditMessage(chatId, messageId,"Please select functionality", new String[][]{{"Booking", "Last Information"}, {"Cancel Booking", "Change Booking"}, {"Back"}}, new String[][]{{"BOOK", "INFO"}, {"CANCEL", "CHANGE"}, {"Back"}});
//      case SETTINGS -> editMessageText = utils.getEditMessage(chatId, messageId, "Please select action", new String[][]{{"Change Phone Number", "Edit Account Information"}, {"Change Language", "Report"}, {"Back"}}, new String[][]{{"PHONE", "INFORMATION"}, {"LANGUAGE", "REPORT"}, {"Back"}});
//    }

    userService.save(user);
    return messageText;
  }
}
