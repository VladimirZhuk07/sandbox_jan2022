package com.exadel.sandbox.team2.service.service;

import com.exadel.sandbox.team2.domain.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TelegramService {

    SendMessage getCountries(String chatId, String message, String data);

    SendMessage getCities(String chatId, String message, String country);

    SendMessage getOfficesByCity(String chatId, String message, String city);

    SendMessage getOfficesByCityForOneDay(String chatId, String message, String date, User user);

    SendMessage setBookingType(String chatId, String message, String[][] titles, String[][] commands, String city);

    SendMessage getWorkplaceByMapId(String chatId, String message, String officeId);

    SendMessage bookOneDayWorkplace(String chatId, String message, String workplaceId, User user);
}
