package com.exadel.sandbox.team2.handler;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
import com.exadel.sandbox.team2.handler.base.BaseHandler;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.serivce.service.UserService;
import com.exadel.sandbox.team2.service.LocaleMessageService;
import com.exadel.sandbox.team2.service.service.TelegramService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Клас для адлову каманд і паведамленні, у выпадку:
 * 1) пры адпраўцы паведамлення.
 * 2) Меню(камандным) у самым нізе Тэлеграма.
 *
 * A class for capturing commands and messages, in the case of:
 * 1) When sending a message.
 * 2) The (command) menu at the very bottom is telegram.
 *
 * Класс для отлова команд и сообщения, в случае:
 * 1) При отправке сообщения.
 * 2) Меню(командном) в самом низу телеграмма.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class MessageHandler implements BaseHandler {

  UserService userService;
  TelegramService telegramService;
  LocaleMessageService lms;
  TelegramUtils utils;

  @Override
  public SendMessage handleSendMessage(Update update, User user) {
    String chatId = utils.getChatId(update);
    SendMessage sendMessage;
    if(user.getTelegramState() == null)
      return utils.getSendMessage(chatId, "Command not found");
    String data = update.getMessage().getText();

    switch (user.getTelegramState()) {
      case MAIN_MENU -> sendMessage = utils.getSendMessage(chatId, "Please select your function", new String[][]{{"\uD83D\uDDD2 Menu","\uD83D\uDC64 Account"}, {"\uD83D\uDCD8 Contact",  "⚙️ Settings"}});
      case GET_ACCOUNT_INFO -> sendMessage = utils.getSendMessage(chatId, String.format("\uD83C\uDF1D " + lms.getMessage("account.firstName") + ": %s"
                      + "\n\uD83C\uDF1A " + lms.getMessage("account.lastName") + ": %s"
                      + "\n\uD83D\uDCDE " + lms.getMessage("account.phoneNumber") + ": %s"
                      + "\n\uD83D\uDCE7 " + lms.getMessage("account.email") + ": %s",
              user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail()),
    new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
      case GET_CONTACT -> sendMessage = utils.getSendMessage(chatId, lms.getMessage("contact.contact"),  new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
      case MENU -> sendMessage = utils.getSendMessage(chatId, "Please select functionality", new String[][]{{lms.getMessage("menu.booking"),lms.getMessage("menu.lastInformation")},
              {"⬅ ️Back"}}, new String[][]{{"BOOK", "INFO"}, {"Back"}});
      case SETTINGS -> sendMessage = utils.getSendMessage(chatId, "Please select action", new String[][]{{lms.getMessage("settings.changePhoneNumber"), lms.getMessage("settings.editAccountInformation")},
              {lms.getMessage("settings.changeLanguage"), lms.getMessage("settings.report")}, {"⬅ ️Back"}},
              new String[][]{{"PHONE", "EDIT_INFORMATION"}, {"LANGUAGE", "REPORT"}, {"Back"}});
      case RECURRING_DEFINE_WEEKDAYS -> sendMessage = telegramService.defineRecurringWeekdays(chatId, "You want to book these weekdays ", data, new String[][] {{"⬅ ️Back"}}, new String[][] {{"Back"}}, user);
      case RECURRING_DEFINE_WEEKS -> sendMessage = telegramService.defineRecurringWeeks(chatId, "Please enter start date of your booking in the form of `2022-03-10`", data, new String[][] {{"⬅ ️Back"}}, new String[][] {{"Back"}}, user);
      case RECURRING_ASSIGN_START_WEEKDAY -> sendMessage = telegramService.defineRecurringStartDate(chatId, "Please write till what weekday you want to book in the form of `MONDAY`, only one weekday is allowed", data, user, new String[][] {{"⬅ ️Back"}}, new String[][] {{"Back"}});
      case RECURRING_ASSIGN_END_WEEKDAY -> sendMessage = telegramService.showRecurringOffices(chatId, "Please enter id of office", data, new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}}, user);
      case SELECT_END_DATE -> sendMessage = telegramService.setEndDateForContinuousBooking(chatId, "Please enter end date of your booking in the form of `2022-03-10`", data, user, new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
      case SHOW_OFFICES_CONTINUOUS -> sendMessage = telegramService.getOfficesByCityForContinuous(chatId, "Please enter id of office", data, user, new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
      case SHOW_OFFICES_BY_CITY -> sendMessage = telegramService.getOfficesByCityForOneDay(chatId, "Please enter office id", data, user, new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
      case SHOW_WORKPLACES_BY_OFFICE, SHOW_WORKPLACES_CONTINUOUS, RECURRING_SHOW_WORKPLACES -> sendMessage = telegramService.getWorkplaceByMapId(chatId, "Please enter id to book workplace", data, user, new String[][] {{"⬅ ️Back"}}, new String[][] {{"Back"}});
      case BOOK_ONE_DAY_WORKPLACE -> sendMessage = telegramService.bookWorkplace(chatId, "Workplace is successfully booked", data, user, new String[][] {{"⬅ ️Back to Menu"}}, new String[][] {{"Back"}});
      case DELETE_USER_BOOKING -> sendMessage = telegramService.deleteUserBooking(chatId, "Your booking is successfully canceled", data, user, new String[][] {{"⬅ ️Back To Menu"}}, new String[][] {{"Back"}});
      case CITY_REPORT_DEFINE_BOOKING_FROM, FLOOR_REPORT_DEFINE_BOOKING_FROM, OFFICE_REPORT_DEFINE_BOOKING_FROM -> sendMessage = telegramService.defineId(chatId, "Please enter `booking from` date in the form of `2022-03-01`", data, user);
      case CITY_REPORT_DEFINE_BOOKING_TO -> sendMessage = telegramService.defineDateFrom(chatId, "Please enter `booking to` in the form of `2022-03-01`",
              "Wrong format, please enter `booking from` date in the form of `2022-03-21`", TelegramState.CITY_REPORT_DEFINE_BOOKING_FROM, data, user);
      case FLOOR_REPORT_DEFINE_BOOKING_TO -> sendMessage = telegramService.defineDateFrom(chatId, "Please enter `booking to` in the form of `2022-03-01`",
              "Wrong format, please enter `booking from` date in the form of `2022-03-21`", TelegramState.FLOOR_REPORT_DEFINE_BOOKING_FROM, data, user);
      case OFFICE_REPORT_DEFINE_BOOKING_TO -> sendMessage = telegramService.defineDateFrom(chatId, "Please enter `booking to` date in the form of `2022-03-21`",
              "Wrong format, please enter date for user`s `created date from` in the form of `2022-03-21`", TelegramState.OFFICE_REPORT_DEFINE_BOOKING_FROM, data, user);
      case USER_REPORT_DEFINE_BOOKING_TO -> sendMessage = telegramService.defineDateFrom(chatId, "Please enter `booking to` date in the form of `2022-03-21`",
              "Wrong format, please enter date for user`s `created date from` in the form of `2022-03-21`", TelegramState.ALL_USER_REPORT_DEFINE_CREATE_DATE_FROM, data, user);
      case ALL_USER_REPORT_DEFINE_CREATE_DATE_TO -> sendMessage = telegramService.defineDateFrom(chatId, "Please enter user`s `created date to` in the form of `2022-03-21`",
              "Wrong format, please enter date for user`s `created date from` in the form of `2022-03-21`", TelegramState.ALL_USER_REPORT_DEFINE_CREATE_DATE_FROM, data, user);
      case ALL_OFFICE_REPORT_DEFINE_BOOKING_TO -> sendMessage = telegramService.defineDateFrom(chatId, "Please enter `booking to` date in the form of `2022-03-21`",
              "Wrong format, please enter `booking from` date in the form of `2022-03-21`", TelegramState.ALL_OFFICE_REPORT_DEFINE_BOOKING_FROM, data, user);
      case GET_USER_REPORT -> sendMessage = telegramService.getReport(chatId, data, user, TelegramState.USER_REPORT_DEFINE_BOOKING_TO,
              "Wrong date or `booking from` date exceed `booking to` date, please enter date for `booking to` in the form `2022-03-21`");
      case GET_ALL_USER_REPORT -> sendMessage = telegramService.getReport(chatId, data, user, TelegramState.ALL_USER_REPORT_DEFINE_CREATE_DATE_TO,
              "Wrong date or `created date from` date exceed `created date to` date, please enter date for `created date to` in the form `2022-03-21`");
      case GET_ALL_OFFICE_REPORT -> sendMessage = telegramService.getReport(chatId, data, user, TelegramState.ALL_OFFICE_REPORT_DEFINE_BOOKING_TO,
              "Wrong date or `booking from` date exceed `booking to` date, please enter date for `booking to` in the form `2022-03-21`");
      case GET_CITY_REPORT -> sendMessage = telegramService.getReport(chatId, data, user, TelegramState.CITY_REPORT_DEFINE_BOOKING_TO,
              "Wrong date or `booking from` date exceed `booking to` date, please enter date for `booking to` in the form `2022-03-21`");
      case GET_FLOOR_REPORT -> sendMessage = telegramService.getReport(chatId, data, user, TelegramState.FLOOR_REPORT_DEFINE_BOOKING_TO,
              "Wrong date or `booking from` date exceed `booking to` date, please enter date for `booking to` in the form `2022-03-21`");
      case GET_OFFICE_REPORT -> sendMessage = telegramService.getReport(chatId, data, user, TelegramState.OFFICE_REPORT_DEFINE_BOOKING_TO,
              "Wrong date or `booking from` date exceed `booking to` date, please enter date for `booking to` in the form `2022-03-21`");

      case CHECK_PHONE_NUMBER -> sendMessage = telegramService.changePhoneNumber(chatId, data, user);

      case CHECK_EDIT_FIRSTNAME, CHECK_EDIT_LASTNAME, CHECK_EDIT_PASSWORD  -> sendMessage = telegramService.editAccountInformation(chatId, data, user);


      default -> sendMessage = utils.getSendMessage(chatId, "[MH] -> Command not found");
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
