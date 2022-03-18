package com.exadel.sandbox.team2.service.impl;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.dto.report.*;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.report.ReportService;
import com.exadel.sandbox.team2.serivce.service.CityService;
import com.exadel.sandbox.team2.serivce.service.MapService;
import com.exadel.sandbox.team2.serivce.service.OfficeService;
import com.exadel.sandbox.team2.serivce.service.UserService;
import com.exadel.sandbox.team2.service.TelegramFileService;
import com.exadel.sandbox.team2.service.service.TelegramReportService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TelegramReportServiceImpl implements TelegramReportService {
    private final TelegramUtils utils;
    private final TelegramFileService telegramFileService;

    private final ReportService reportService;
    private final UserService userService;
    private final OfficeService officeService;
    private final MapService mapService;
    private final CityService cityService;

    private final String JRXML_PATH_FOR_USERS_REPORT = "users_pattern.jrxml";
    private final String JRXML_PATH_FOR_SINGLE_OFFICE_REPORT = "single_office_pattern.jrxml";
    private final String JRXML_PATH_FOR_ALL_OFFICES_REPORT = "all_offices_pattern.jrxml";
    private final String JRXML_PATH_FOR_CITY_REPORT = "city_pattern.jrxml";
    private final String JRXML_PATH_FOR_FLOOR_REPORT = "floor_pattern.jrxml";
    private final String JRXML_PATH_FOR_EMPLOYEES_REPORT = "employees_pattern.jrxml";

    private final String REPORT_ON_SINGLE_OFFICE_FOLDER = "ReportOnSingleOffice";
    private final String REPORT_ON_ALL_OFFICES_FOLDER = "ReportOnAllOffices";
    private final String REPORT_ON_CITY_FOLDER = "ReportOnCity";
    private final String REPORT_ON_FLOOR_FOLDER = "ReportOnFloor";
    private final String REPORT_ON_USERS_FOLDER = "ReportOnUsers";
    private final String REPORT_ON_EMPLOYEES_FOLDER = "ReportOnEmployees";
    private final String PREFIX_FOR_REPORT_FOLDER = "./REPORT/";

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Date defaultDateFrom = new GregorianCalendar(1990, Calendar.JANUARY, 1).getTime();
    private final Date defaultDateTo = Date.from((LocalDate.now().plusYears(50)).atStartOfDay(ZoneId.systemDefault()).toInstant());


    @SneakyThrows
    @PostConstruct
    public void initialize() {
        checkOrCreateFolder(PREFIX_FOR_REPORT_FOLDER + REPORT_ON_SINGLE_OFFICE_FOLDER);
        checkOrCreateFolder(PREFIX_FOR_REPORT_FOLDER + REPORT_ON_ALL_OFFICES_FOLDER);
        checkOrCreateFolder(PREFIX_FOR_REPORT_FOLDER + REPORT_ON_CITY_FOLDER);
        checkOrCreateFolder(PREFIX_FOR_REPORT_FOLDER + REPORT_ON_FLOOR_FOLDER);
        checkOrCreateFolder(PREFIX_FOR_REPORT_FOLDER + REPORT_ON_USERS_FOLDER);
        checkOrCreateFolder(PREFIX_FOR_REPORT_FOLDER + REPORT_ON_EMPLOYEES_FOLDER);
    }

    @SneakyThrows
    private void checkOrCreateFolder(String nameOfFolder) {
        if (!Files.exists(Path.of(nameOfFolder))) {
            Files.createDirectories(Path.of(nameOfFolder));
        }
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportOnSingleOffice(User user, Long idOfOffice, Date bookDateFrom, Date bookDateTo) {
        bookDateFrom = setDefaultDateFromIfNull(bookDateFrom);
        bookDateTo = setDefaultDateToIfNull(bookDateTo);

        List<ReportOnSingleOfficeDto> reportData = officeService.getDataForReportBySingleOffice(idOfOffice, bookDateFrom, bookDateTo);
        if (reportData.isEmpty()) {
            return messageIfNullQuery(user.getChatId(), bookDateFrom, bookDateTo);
        } else {
            java.util.Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            parameters.put("bookDateFrom", dateFormat.format(bookDateFrom));
            parameters.put("bookDateTo", dateFormat.format(bookDateTo));
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_SINGLE_OFFICE_REPORT, parameters,
                    REPORT_ON_SINGLE_OFFICE_FOLDER.concat("/")
                            .concat(REPORT_ON_SINGLE_OFFICE_FOLDER)
                            .concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return byeMessage(user.getChatId(), "single office");
        }
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportOnAllOffices(User user, Date bookDateFrom, Date bookDateTo) {
        bookDateFrom = setDefaultDateFromIfNull(bookDateFrom);
        bookDateTo = setDefaultDateToIfNull(bookDateTo);

        List<ReportOnAllOfficesDto> reportData = officeService.getDataForReportByAllOffices(bookDateFrom, bookDateTo);
        if (reportData.isEmpty()) {
            return messageIfNullQuery(user.getChatId(), bookDateFrom, bookDateTo);
        } else {
            java.util.Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            parameters.put("bookDateFrom", dateFormat.format(bookDateFrom));
            parameters.put("bookDateTo", dateFormat.format(bookDateTo));
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_ALL_OFFICES_REPORT, parameters,
                    REPORT_ON_ALL_OFFICES_FOLDER.concat("/")
                            .concat(REPORT_ON_ALL_OFFICES_FOLDER)
                            .concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return byeMessage(user.getChatId(), "all offices");
        }
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportOnUsers(User user, Date bookDateFrom, Date bookDateTo) {
        bookDateFrom = setDefaultDateFromIfNull(bookDateFrom);
        bookDateTo = setDefaultDateToIfNull(bookDateTo);

        List<ReportOnUsersDto> reportData = userService.getDataForReportOnUsers(bookDateFrom, bookDateTo);
        if (reportData.isEmpty()) {
            return messageIfNullQuery(user.getChatId(), bookDateFrom, bookDateTo);
        } else {
            java.util.Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            parameters.put("bookDateFrom", dateFormat.format(bookDateFrom));
            parameters.put("bookDateTo", dateFormat.format(bookDateTo));
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_USERS_REPORT, parameters,
                    REPORT_ON_USERS_FOLDER.concat("/")
                            .concat(REPORT_ON_USERS_FOLDER)
                            .concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return byeMessage(user.getChatId(), "users");
        }
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportOnCity(User user, Long idOfCity, Date bookDateFrom, Date bookDateTo) {
        bookDateFrom = setDefaultDateFromIfNull(bookDateFrom);
        bookDateTo = setDefaultDateToIfNull(bookDateTo);

        List<ReportOnCityDto> reportData = cityService.getDataForReportOnCity(idOfCity, bookDateFrom, bookDateTo);
        if (reportData.isEmpty()) {
            return messageIfNullQuery(user.getChatId(), bookDateFrom, bookDateTo);
        } else {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            parameters.put("bookedDateFrom", dateFormat.format(bookDateFrom));
            parameters.put("bookedDateTo", dateFormat.format(bookDateTo));
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_CITY_REPORT, parameters,
                    REPORT_ON_CITY_FOLDER.concat("/")
                            .concat(REPORT_ON_CITY_FOLDER)
                            .concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return byeMessage(user.getChatId(), "city");
        }
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportOnFloor(User user, Long idOfFloor, Date bookDateFrom, Date bookDateTo) {
        bookDateFrom = setDefaultDateFromIfNull(bookDateFrom);
        bookDateTo = setDefaultDateToIfNull(bookDateTo);

        List<ReportOnFloorDto> reportData = mapService.getDataForReportOnFloor(idOfFloor, bookDateFrom, bookDateTo);
        if (reportData.isEmpty()) {
            return messageIfNullQuery(user.getChatId(), bookDateFrom, bookDateTo);
        } else {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            parameters.put("idOfFloor", idOfFloor);
            parameters.put("bookedDateFrom", dateFormat.format(bookDateFrom));
            parameters.put("bookedDateTo", dateFormat.format(bookDateTo));
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_FLOOR_REPORT, parameters,
                    REPORT_ON_FLOOR_FOLDER.concat("/")
                            .concat("REPORT_ON_FLOOR_FOLDER")
                            .concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return byeMessage(user.getChatId(), "floor");
        }
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportOnEmployees(User user, Date userCreateDateFrom, Date userCreateDateTo) {
        userCreateDateFrom = setDefaultDateFromIfNull(userCreateDateFrom);
        userCreateDateTo = setDefaultDateToIfNull(userCreateDateTo);

        List<ReportOnEmployeesDto> reportData = userService.getDataForEmployeesReport(userCreateDateFrom, userCreateDateTo);
        if (reportData.isEmpty()) {
            return messageIfNullQuery(user.getChatId(), userCreateDateFrom, userCreateDateTo);
        } else {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            parameters.put("userCreateDateFrom", dateFormat.format(userCreateDateFrom));
            parameters.put("userCreateDateTo", dateFormat.format(userCreateDateTo));
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_EMPLOYEES_REPORT, parameters,
                    REPORT_ON_EMPLOYEES_FOLDER.concat("/")
                            .concat(REPORT_ON_EMPLOYEES_FOLDER)
                            .concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return byeMessage(user.getChatId(), "employees");
        }
    }

    private SendMessage messageIfNullQuery(String chatId, Date bookDateFrom, Date bookDateTo) {
        StringBuilder builder = new StringBuilder();
        builder.append("Sorry, but for the period from  ")
                .append(bookDateFrom)
                .append(" to ")
                .append(bookDateTo)
                .append(" there is no any information. You can try again, choosing another period of time. \uD83E\uDDF8");
        return utils.getSendMessage(chatId, builder.toString());
    }

    private Date setDefaultDateFromIfNull(Date dateFrom) {
        return (null == dateFrom) ? defaultDateFrom : dateFrom;
    }

    private Date setDefaultDateToIfNull(Date dateTo) {
        return (null == dateTo) ? defaultDateTo : dateTo;
    }

    private SendMessage byeMessage(String chatId, String typeOfReport) {
        StringBuilder builder = new StringBuilder();
        builder.append("There is your report on ").append(typeOfReport).append(", you are welcome!\nHave a nice day to you \uD83E\uDDF8☘️");
        return utils.getSendMessage(chatId, builder.toString());
    }
}
