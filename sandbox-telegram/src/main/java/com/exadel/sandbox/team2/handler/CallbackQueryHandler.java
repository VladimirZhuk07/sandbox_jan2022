package com.exadel.sandbox.team2.handler;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
import com.exadel.sandbox.team2.handler.base.BaseHandler;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.report.PdfReportServiceImpl;
import com.exadel.sandbox.team2.serivce.service.UserService;
import com.exadel.sandbox.team2.service.LocaleMessageService;
import com.exadel.sandbox.team2.service.TelegramFileService;
import com.exadel.sandbox.team2.service.service.TelegramReportService;
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
  TelegramReportService telegramReportService;
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
      //TODO
      case UPDATE_PHONE_NUMBER -> sendMessage = utils.getSendMessage(chatId,
              lms.getMessage("cBQH.reply.enterYourNewNumber"), new String[][]{{"◀️Back"}}, new String[][]{{"Back"}});

      case CHOOSE_COUNTRY -> sendMessage = telegramService.getCountries(chatId, "Please select country", data);
      case CHOOSE_CITY -> sendMessage = telegramService.getCities(chatId, "Please select city", data);
      case ASSIGN_BOOKING_TYPE -> sendMessage = telegramService.setBookingType(chatId, "Please, select booking type", new String[][] {{"One day"},{"Continuous"},{"Recurring"},{"Back"}}, new String[][] {{"One day"},{"Continuous"},{"Recurring"},{"Back"}}, data);
      case ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED, CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED, RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED -> sendMessage = telegramService.isWorkplaceNeedBeDefine(chatId, "Do you want to define office/workplace attributes?", new String[][]{{"Yes","No"},{"Back"}}, new String[][]{{"DEFINE_WORKPLACE_ATTRIBUTES", "NOT_DEFINE_WORKPLACE_ATTRIBUTES"},{"Back"}}, data);
      case ONE_DAY_IS_KITCHEN_NEED, CONTINUOUS_IS_KITCHEN_NEED, RECURRING_IS_KITCHEN_NEED -> sendMessage = telegramService.isKitchenNeed(chatId, "Should there be a kitchen?\nIf not, please press Next", new String[][]{{"Yes"},{"Back","Next"}}, new String[][]{{"Yes"},{"Back","Next"}}, user, data);
      case BACK_TO_IS_KITCHEN_NEED -> sendMessage = telegramService.backToIsKitchenNeed(chatId, user);
      case IS_CONFERENCE_HALL_NEED -> sendMessage = telegramService.isConferenceHallNeed(chatId, "Should there be conference hall?\nIf not, then please press Next",
              new String[][]{{"Yes"},{"Back","Next"}}, new String[][]{{"Yes"},{"Back","Next"}}, data);
      case IS_NEXT_TO_WINDOWS_NEED_BE -> sendMessage = telegramService.isNextToWindowNeedBe(chatId, "Should it be next to window?\nIf no, then please press No.\nIf does not matter then press Next",
              new String[][]{{"Yes","No"},{"Back","Next"}}, new String[][]{{"Yes","No"},{"Back","Next"}}, data);
      case IS_PC_NEED_BE -> sendMessage = telegramService.isPcNeedBe(chatId, "Should there be pc?\nIf no, then please press No.\nIf does not matter then press Next",
              new String[][]{{"Yes","No"},{"Back","Next"}}, new String[][]{{"Yes","No"},{"Back","Next"}}, data);
      case IS_MONITOR_NEED_BE -> sendMessage = telegramService.isMonitorNeedBe(chatId, "Should there be monitor?\nIf no, then please press No.\nIf does not matter then press Next",
              new String[][]{{"Yes","No"},{"Back","Next"}}, new String[][]{{"Yes","No"},{"Back","Next"}}, data);
      case IS_KEYBOARD_NEED_BE -> sendMessage = telegramService.isKeyboardNeedBe(chatId, "Should there be keyboard?\nIf no, then please press No.\nIf does not matter then press Next",
              new String[][]{{"Yes","No"},{"Back","Next"}}, new String[][]{{"Yes","No"},{"Back","Next"}}, data);
      case IS_MOUSE_NEED_BE -> sendMessage = telegramService.isMouseNeedBe(chatId, "Should there be mouse?\nIf no, then please press No.\nIf does not matter then press Next",
              new String[][]{{"Yes","No"},{"Back","Next"}}, new String[][]{{"Yes","No"},{"Back","Next"}}, data);
      case IS_HEADSET_NEED_BE -> sendMessage = telegramService.isHeadsetNeedBe(chatId, "Should there be headset?\nIf no, then please press No.\nIf does not matter then press Next",
              new String[][]{{"Yes","No"},{"Back","Next"}}, new String[][]{{"Yes","No"},{"Back","Next"}}, data);
      case FINISH_DEFINE_WORKPLACE_ATTRIBUTES -> sendMessage = telegramService.finishDefineWorkplaceAttributes(chatId, user, data);
      case BACK_FROM_SELECT_ONE_DAY_DATE,BACK_FROM_SELECT_CONTINUOUS_DATE,BACK_FROM_SELECT_RECURRING_DATE -> sendMessage = telegramService.backFromSelectDateOrSelectWeekDay(chatId, user, data);
      case ONE_DAY_SELECT_DATE, CONTINUOUS_SELECT_DATE -> sendMessage = utils.getSendMessage(chatId, "Please write start date of your booking in form of `2022-03-10`", new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case RECURRING_SELECT_WEEK_DAY -> sendMessage = utils.getSendMessage(chatId, "Please write all weekdays you want to book in the format 'MONDAY,TUESDAY,WEDNESDAY'", new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case SHOW_OFFICES_BY_CITY -> sendMessage = telegramService.getOfficesByCityForOneDay(chatId, "Please enter office id", data, user, new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case RECURRING_DEFINE_WEEKDAYS -> sendMessage = telegramService.defineRecurringWeekdays(chatId, "You want to book these weekdays ", data, new String[][] {{"Back"}}, new String[][] {{"Back"}}, user);
      case RECURRING_DEFINE_WEEKS -> sendMessage = telegramService.defineRecurringWeeks(chatId, "Please enter start date of your booking in the form of `2022-03-10`", data, new String[][] {{"Back"}}, new String[][] {{"Back"}}, user);
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
      //TODO
      case SETTINGS -> sendMessage = utils.getSendMessage(chatId, "Please select action",
              new String[][]{{lms.getMessage("settings.changePhoneNumber"), lms.getMessage("settings.editAccountInformation")},
                      {lms.getMessage("settings.changeLanguage"), lms.getMessage("settings.report")}, {"Back"}},
              new String[][]{{"PHONE", "EDIT_INFORMATION"}, {"LANGUAGE", "REPORT"}, {"Back"}});

      case REPORT -> sendMessage = telegramService.checkUserRole(chatId, "Please select report type.",
              new String[][]{{"User", "All User"}, {"City", "Single Office"}, {"Offices", "Floor"}, {"Back"}},
              new String[][]{{"USER_REPORT", "ALL_USER_REPORT"}, {"CITY_REPORT", "SINGLE_OFFICE_REPORT"}, {"OFFICES_REPORT", "FLOOR_REPORT"}, {"Back"}}, user);
      case USER_REPORT_DEFINE_BOOKING_FROM -> sendMessage = utils.getSendMessage(chatId, "Please enter 'booking from' date in the form of '2022-03-21", new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case ALL_USER_REPORT_DEFINE_CREATE_DATE_FROM -> sendMessage = utils.getSendMessage(chatId, "Please enter user`s 'created date' in the form of '2022-03-21'", new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case ALL_OFFICE_REPORT_DEFINE_BOOKING_FROM -> sendMessage = utils.getSendMessage(chatId, "Please enter 'booking from' date in the form of '2022-03-21'", new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case CITY_REPORT_DEFINE_ID -> sendMessage = utils.getSendMessage(chatId, "Please enter city name in uppercase", new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case OFFICE_REPORT_DEFINE_ID -> sendMessage = utils.getSendMessage(chatId, "Please enter office id", new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case FLOOR_REPORT_DEFINE_FLOOR -> sendMessage = utils.getSendMessage(chatId, "Please enter floor number", new String[][]{{"Back"}}, new String[][]{{"Back"}});
      case CITY_REPORT_DEFINE_BOOKING_FROM, FLOOR_REPORT_DEFINE_BOOKING_FROM -> sendMessage = telegramService.defineId(chatId, "Please enter 'booking from' date in the form of '2022-03-01'", data, user);
      //TODO
      case EDIT_INFORMATION -> sendMessage = utils.getSendMessage(chatId, "Please select what you want to edit",
              new String[][]{{"Firstname", "Lastname"},
                      {"\uD83D\uDD10 Password"}, {"\uD83D\uDD19 Back"}},
              new String[][]{{"EDIT_FIRSTNAME", "EDIT_LASTNAME"}, {"EDIT_PASSWORD"}, {"Back"}});

      case EDIT_FIRSTNAME -> sendMessage = utils.getSendMessage(chatId,
              "Enter your new Firstname", new String[][]{{"◀️Back"}}, new String[][]{{"Back"}});
      case EDIT_LASTNAME ->sendMessage = utils.getSendMessage(chatId,
              "Enter your new Lastname", new String[][]{{"◀️Back"}}, new String[][]{{"Back"}});
      case EDIT_PASSWORD -> sendMessage = utils.getSendMessage(chatId,
              "Enter your new Password", new String[][]{{"◀️Back"}}, new String[][]{{"Back"}});

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
