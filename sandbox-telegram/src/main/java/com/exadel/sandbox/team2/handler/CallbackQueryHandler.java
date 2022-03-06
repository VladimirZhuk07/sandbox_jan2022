package com.exadel.sandbox.team2.handler;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
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
public class CallbackQueryHandler implements BaseHandler {

  private final UserService userService;
  TelegramUtils utils;

  @Override
  public SendMessage handle(Update update, User user) {
    String chatId = utils.getChatId(update);
    String command =update.getCallbackQuery().getData();
    SendMessage sendMessage;

    switch (command){
      case "PHONE" -> {
        user.setTelegramState(TelegramState.UPDATE_PHONE_NUMBER);
        sendMessage = utils.getMessage(chatId, "Enter your new number", "Please send your number!");
      }
      default -> sendMessage = utils.getMessage(chatId, "We work with this command: ".concat(command));
    }
    userService.save(user);
    return sendMessage;
  }
}
