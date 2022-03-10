package com.exadel.sandbox.team2.handler;

import com.exadel.sandbox.team2.domain.Booking;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.dto.UsersPOJO;
import com.exadel.sandbox.team2.handler.base.BaseHandler;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.report.PdfReportServiceImpl;
import com.exadel.sandbox.team2.serivce.service.UserService;
import com.exadel.sandbox.team2.service.LocaleMessageService;
import com.exadel.sandbox.team2.service.TelegramFileService;
import com.exadel.sandbox.team2.service.service.TelegramService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class CallbackQueryHandler implements BaseHandler {

  LocaleMessageService lms;
  TelegramFileService fileService;
  PdfReportServiceImpl reportService;
  UserService userService;
  TelegramService telegramService;
  TelegramUtils utils;

  /*@SneakyThrows
  private SendMessage sendMessage(String chatId, User user) {
    List<UsersPOJO> usersPOJOS = new ArrayList<>();
    List<User> users = userService.findAll();
    // TODO дата начала букинга и даата конца букинга \
    // TODO Users POJO в ReportDTO
    users.forEach(u -> {
      List<Booking> bookings = u.getBookings();
      bookings.forEach(b -> {
        UsersPOJO user1= new UsersPOJO();
        user1.setFirstName(u.getFirstName());
        user1.setLastName(u.getLastName());
        user1.setTimeStart(b.getStartDate());
        user1.setTimeEnd(b.getEndDate());
        user1.setWorkplaceID(b.getWorkplace().getId());
        usersPOJOS.add(user1);
      });
    });
    String filePath = reportService.getReport(usersPOJOS,"users.jrxml", user.getChatId());
    fileService.sendDocument(chatId,filePath);
    return utils.getSendMessage(chatId,"Report Successfully Sent!");
  }*/

  @Override
  public SendMessage handleSendMessage(Update update, User user) {
    String chatId = utils.getChatId(update);
    String data = update.getCallbackQuery().getData();
    SendMessage sendMessage;

    switch (user.getTelegramState()){
      case MAIN_MENU -> sendMessage = utils.getSendMessage(chatId, "Please select your function", new String[][]{{"\uD83D\uDDD2 Menu","\uD83D\uDC64 Account"}, {"\uD83D\uDCD8 Contact",  "⚙️ Settings"}});
      case UPDATE_PHONE_NUMBER -> sendMessage = utils.getSendMessage(chatId, lms.getMessage("cBQH.reply.enterYourNewNumber"), lms.getMessage("cBQH.reply.pleaseSendYourNumber"));
      case CHOOSE_COUNTRY -> sendMessage = telegramService.getCountries(chatId, "Please select country", data);
      case CHOOSE_CITY -> sendMessage = telegramService.getCities(chatId, "Please select city", data);
      case ASSIGN_BOOKING_TYPE -> sendMessage = telegramService.setBookingType(chatId, "Please, select booking type", new String[][] {{"One day"},{"Continuous"},{"Recurring"}}, new String[][] {{"One day"},{"Continuous"},{"Recurring"}}, data);
      case ONE_DAY_SELECT_DATE, CONTINUOUS_SELECT_DATE -> sendMessage = utils.getSendMessage(chatId, "Please write date in form of `2020-01-08`");
      case CHOOSE_LANGUAGE -> sendMessage = utils.getSendMessage(chatId, lms.getMessage("settings.pleaseSelectLanguage"),
              new String[][]{{lms.getMessage("language.belarusian"), lms.getMessage("language.english")},
                      {lms.getMessage("language.uzbek"), lms.getMessage("language.russian")}},
              new String[][]{{"BY", "EN"}, {"UZ", "RU"}});
      case SET_LANGUAGE -> sendMessage = setLang(data, chatId);
     // case REPORT -> sendMessage = this.sendMessage(chatId,user);
      default -> sendMessage = utils.getSendMessage(chatId, lms.getMessage("cBQH.status.weWorkWithThisCommand").concat(" ").concat(user.getTelegramState().toString()));
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

  private SendMessage getMessageChanged(String chatId) {
    return utils.getSendMessage(chatId, lms.getMessage("status.languageChanged").concat(lms.getCurrentLanguage()));
  }

  private SendMessage setLang(String lan, String chatId){
    switch (lan){
      case "BY" -> lms.setLocale("by-BY");
      case "EN" -> lms.setLocale("en-EN");
      case "UZ" -> lms.setLocale("uz-UZ");
      case "RU" -> lms.setLocale("ru-RU");
    }
    return getMessageChanged(chatId);
  }

}
