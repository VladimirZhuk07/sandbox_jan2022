package com.exadel.sandbox.team2.handler;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.handler.base.BaseHandler;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.report.PdfReportServiceImpl;
import com.exadel.sandbox.team2.serivce.service.UserService;
import com.exadel.sandbox.team2.service.TelegramFileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

  TelegramUtils utils;
  TelegramFileService fileService;
  PdfReportServiceImpl reportService;
  UserService userService;

  @SneakyThrows
  @Override
  public SendMessage handle(Update update, User user) {
    String chatId = utils.getChatId(update);
    String command =update.getCallbackQuery().getData();

    return switch (command){
      case "PHONE" -> utils.getMessage(chatId, "Enter your new number", "Please send your number!");
      case "REPORT" -> sendMessage(chatId,user);
      default -> utils.getMessage(chatId, "We work with this command: ".concat(command));
    };

  }

  @SneakyThrows
  private SendMessage sendMessage(String chatId, User user){
    String filePath = reportService.getReport(userService.findAll(),"users.jrxml", user.getChatId());
    fileService.sendDocument(chatId,filePath);
    return utils.getMessage(chatId,"Report Successfully Sent!");
  }
}
