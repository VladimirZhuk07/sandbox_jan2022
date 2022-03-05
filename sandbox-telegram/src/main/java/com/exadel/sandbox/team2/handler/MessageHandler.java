package com.exadel.sandbox.team2.handler;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.handler.base.BaseHandler;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.serivce.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class MessageHandler implements BaseHandler {

  TelegramUtils utils;

  @Override
  public SendMessage handle(Update update, User user) {
    String chatId = utils.getChatId(update);
    String text = update.getMessage().getText();

    return switch (text) {
      case "\uD83D\uDDD2 Menu" -> utils.getMessage(chatId, "Please select functionality", new String[][]{{"Booking", "Last Information"}, {"Cancel Booking", "Change Booking"}}, new String[][]{{"BOOK", "INFO"}, {"CANCEL", "CHANGE"}});
      case "\uD83D\uDC64 Account" -> utils.getMessage(chatId, String.format("First Name: %s\nLast Name: %s\nPhone Number: %s", user.getFirstName(), user.getLastName(), user.getPhoneNumber()));
      case "\uD83D\uDCD8 Contact" -> utils.getMessage(chatId, "Contact: @nurmukhammadsunatullaev");
      case "⚙️ Settings" -> utils.getMessage(chatId, "Please select action", new String[][]{{"Change Phone Number", "Edit Account Information"}, {"Change Language", "Report"}}, new String[][]{{"PHONE", "INFORMATION"}, {"LANGUAGE", "REPORT"}});
      default -> utils.getMessage(chatId, "Command not found");
    };

  }
}
