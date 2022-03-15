package com.exadel.sandbox.team2.service.service;

import com.exadel.sandbox.team2.domain.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Date;

public interface TelegramReportService {
    SendMessage sendReportOnEmployees(User user, Date dateFrom, Date dateTo);

    SendMessage sendReportOnSingleOffice(User user, Long idOfOffice, Date modifiedDateFrom, Date modifiedDateTo);

    SendMessage sendReportOnAllOffices(User user, Date modifiedDateFrom, Date modifiedDateTo);

    SendMessage sendReportOnCity(User user, Long idOfCity, Date bookedDateFrom, Date bookedDateTo);

    SendMessage sendReportOnFloor(User user, Long idOfFloor, Date bookedDateFrom, Date bookedDateTo);
}
