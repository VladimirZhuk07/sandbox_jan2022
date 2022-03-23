package com.exadel.sandbox.team2.service.service;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
import com.exadel.sandbox.team2.dto.telegram.CreateBookingDto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Date;

public interface TelegramService {

    SendMessage getCountries(String chatId, String message, String data);

    SendMessage getCities(String chatId, String message, String country);

    SendMessage setBookingType(String chatId, String message, String[][] titles, String[][] commands, String city);

    SendMessage isWorkplaceNeedBeDefine(String chatId, String message, String[][] titles, String[][] commands, String data);

    SendMessage isKitchenNeed(String chatId, String message, String[][] titles, String[][] commands, User user, String data);

    SendMessage backToIsKitchenNeed(String chatId, User user);

    SendMessage isConferenceHallNeed(String chatId, String message, String[][] titles, String[][] commands, String data);

    SendMessage isNextToWindowNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data);

    SendMessage isPcNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data);

    SendMessage isMonitorNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data);

    SendMessage isKeyboardNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data);

    SendMessage isMouseNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data);

    SendMessage isHeadsetNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data);

    SendMessage finishDefineWorkplaceAttributes(String chatId, User user, String data);

    SendMessage backFromSelectDateOrSelectWeekDay(String chatId, User user, String date);

    SendMessage setEndDateForContinuousBooking(String chatId, String message, String startDate, User user, String[][] titles, String[][] commands);

    SendMessage defineRecurringWeekdays(String chatId, String message, String weekdays, String[][] titles, String[][] commands, User user);

    SendMessage defineRecurringWeeks(String chatId, String message, String weekTimes, String[][] titles, String[][] commands, User user);

    SendMessage defineRecurringStartDate(String chatId, String message, String startDate, User user, String[][] titles, String[][] commands);

    SendMessage getOfficesByCity(String chatId, String message, String city, String[][] titles, String[][] commands, CreateBookingDto dto);

    SendMessage getOfficesByCityForOneDay(String chatId, String message, String date, User user, String[][] titles, String[][] commands);

    SendMessage showRecurringOffices(String chatId, String message, String endWeekday, String[][] titles, String[][] commands, User user);

    SendMessage getOfficesByCityForContinuous(String chatId, String message, String date, User user, String[][] titles, String[][] commands);

    SendMessage getWorkplaceByMapId(String chatId, String message, String officeId, User user, String[][] titles, String[][] commands);

    SendMessage bookWorkplace(String chatId, String message, String workplaceId, User user, String[][] titles, String[][] commands);

    SendMessage getUserBookings(String chatId, String message, Long userId, String[][] titles, String[][] commands);

    SendMessage deleteUserBooking(String chatId, String message, String bookingId, User user, String[][] titles, String[][] commands);

    SendMessage checkUserRole(String chatId, String message, String[][] titles, String[][] commands, User user);

    SendMessage defineId(String chatId, String message, String id, User user);

    SendMessage defineDateFrom(String chatId, String message, String errorMessage, TelegramState telegramState, String createdDateFrom, User user);

    SendMessage getReport(String chatId, String dateTo, User user, TelegramState telegramState, String message);

    SendMessage changePhoneNumber(String chatId, String newPhoneNumber, User user);

    SendMessage editAccountInformation(String chatId, String newData, User user);
}
