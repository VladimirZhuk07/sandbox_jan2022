package com.exadel.sandbox.team2.service.service;

import com.exadel.sandbox.team2.domain.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Date;

public interface TelegramReportService {
    SendMessage sendReportOnEmployees(String chatId, User user, Date dateFrom, Date dateTo);

    SendMessage sendReportOnSingleOffice(String chatId, User user, Long idOfOffice, Date modifiedDateFrom, Date modifiedDateTo);

    SendMessage sendReportOnAllOffices(String chatId, User user, Date modifiedDateFrom, Date modifiedDateTo);

    SendMessage sendReportOnCity(String chatId, User user, Long idOfCity, Date bookedDateFrom, Date bookedDateTo);

    SendMessage sendReportOnFloor(String chatId, User user, Long idOfFloor, Date bookedDateFrom, Date bookedDateTo);
}
