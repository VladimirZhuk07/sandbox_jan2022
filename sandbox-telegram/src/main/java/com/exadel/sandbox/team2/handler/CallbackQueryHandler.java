package com.exadel.sandbox.team2.handler;

import com.exadel.sandbox.team2.domain.User;
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

  @SneakyThrows
  private SendMessage sendMessage(String chatId, User user){
    String filePath = reportService.getReport(userService.findAll(),"users.jrxml", user.getChatId());
    fileService.sendDocument(chatId,filePath);
    return utils.getSendMessage(chatId,"Report Successfully Sent!");
  }

  @Override
  public SendMessage handleSendMessage(Update update, User user) {
    String chatId = utils.getChatId(update);
    SendMessage sendMessage;
    if(user.getTelegramState() == null)
      return utils.getSendMessage(chatId, "Command not found");
    String data = update.getCallbackQuery().getData();


    switch (user.getTelegramState()){
      case MAIN_MENU -> sendMessage = utils.getSendMessage(chatId, "Please select your function", new String[][]{{"\uD83D\uDDD2 Menu","\uD83D\uDC64 Account"}, {"\uD83D\uDCD8 Contact",  "⚙️ Settings"}});
      case MENU -> sendMessage = utils.getSendMessage(chatId, "Please select functionality", new String[][]{{lms.getMessage("menu.booking"),lms.getMessage("menu.lastInformation")},
              {"Back"}}, new String[][]{{"BOOK", "INFO"}, {"Back"}});
      case UPDATE_PHONE_NUMBER -> sendMessage = utils.getSendMessage(chatId, lms.getMessage("cBQH.reply.enterYourNewNumber"), lms.getMessage("cBQH.reply.pleaseSendYourNumber"));
      case CHOOSE_COUNTRY -> sendMessage = telegramService.getCountries(chatId, "Please select country", data);
      case CHOOSE_CITY -> sendMessage = telegramService.getCities(chatId, "Please select city", data);
      case ASSIGN_BOOKING_TYPE -> sendMessage = telegramService.setBookingType(chatId, "Please, select booking type", new String[][] {{"One day"},{"Continuous"},{"Recurring"},{"Back"}}, new String[][] {{"One day"},{"Continuous"},{"Recurring"},{"Back"}}, data);
      case ONE_DAY_SELECT_DATE, CONTINUOUS_SELECT_DATE -> sendMessage = utils.getSendMessage(chatId, "Please write start date of your booking in form of `2022-03-10`", new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case SHOW_OFFICES_BY_CITY -> sendMessage = telegramService.getOfficesByCityForOneDay(chatId, "Please enter office id", data, user, new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case RECURRING_SELECT_WEEK_DAY -> sendMessage = utils.getSendMessage(chatId, "Please write all weekdays you want to book in the format 'MONDAY,TUESDAY,WEDNESDAY'", new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case RECURRING_DEFINE_WEEKDAYS -> sendMessage = telegramService.defineRecurringWeekdays(chatId, "You want to book these weekdays ", data, new String[][] {{"Back"}}, new String[][] {{"Back"}}, user);
      case RECURRING_DEFINE_WEEKS -> sendMessage = telegramService.defineRecurringWeeks(chatId, "Please enter start date of your booking in the form of `2022-03-10`", data, new String[][] {{"Back"}}, new String[][] {{"Back"}});
      case CHOOSE_LANGUAGE -> sendMessage = utils.getSendMessage(chatId, lms.getMessage("settings.pleaseSelectLanguage"),
              new String[][]{{lms.getMessage("language.belarusian"), lms.getMessage("language.english")},
                      {lms.getMessage("language.uzbek"), lms.getMessage("language.russian")}},
              new String[][]{{"BY", "EN"}, {"UZ", "RU"}});
      case SET_LANGUAGE -> sendMessage = setLang(data, chatId);
      case GET_USER_BOOKINGS -> sendMessage = telegramService.getUserBookings(chatId, "Here is your active bookings", user.getId(), new String[][]{{"Cancel Booking"},{"Back"}}, new String[][]{{"CANCEL_BOOKING"},{"Back"}});
      case CANCEL_BOOKING -> sendMessage = utils.getSendMessage(chatId, "Please enter id of your booking to cancel it");
      case SELECT_END_DATE -> sendMessage = telegramService.setEndDateForContinuousBooking(chatId, "Please enter end date of your booking in the form of `2022-03-10`", data, user, new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case SHOW_OFFICES_CONTINUOUS -> sendMessage = telegramService.getOfficesByCityForContinuous(chatId, "Please enter id of office", data, user, new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case RECURRING_ASSIGN_START_WEEKDAY -> sendMessage = telegramService.defineRecurringStartDate(chatId, "Please write till what weekday you want to book in the form of 'MONDAY', only one weekday is allowed", data, user, new String[][] {{"Back"}}, new String[][] {{"Back"}});
      case RECURRING_ASSIGN_END_WEEKDAY -> sendMessage = telegramService.showRecurringOffices(chatId, "Please enter id of office", data, new String[][]{{"Back"}}, new String[][]{{"Back"}}, user);
      default -> sendMessage = utils.getSendMessage(chatId, lms.getMessage("cBQH.status.weWorkWithThisCommand"));
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
