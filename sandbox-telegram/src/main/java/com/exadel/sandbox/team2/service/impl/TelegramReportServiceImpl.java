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

    private final String JRXML_PATH_FOR_EMPLOYEES_REPORT = "users_pattern.jrxml";
    private final String JRXML_PATH_FOR_SINGLE_OFFICE_REPORT = "single_office_pattern.jrxml";
    private final String JRXML_PATH_FOR_ALL_OFFICES_REPORT = "all_offices_pattern.jrxml";
    private final String JRXML_PATH_FOR_CITY_REPORT = "city_pattern.jrxml";
    private final String JRXML_PATH_FOR_FLOOR_REPORT = "floor_pattern.jrxml";

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Date defaultDateFrom = new GregorianCalendar(1990, Calendar.JANUARY, 1).getTime();
    private final Date defaultDateTo = Date.from((LocalDate.now().plusYears(50)).atStartOfDay(ZoneId.systemDefault()).toInstant());


    @SneakyThrows
    @Override
    public SendMessage sendReportOnSingleOffice(User user, Long idOfOffice, Date modifiedDateFrom, Date modifiedDateTo) {
        modifiedDateFrom = (null == modifiedDateFrom) ? defaultDateFrom : modifiedDateFrom;
        modifiedDateTo = (null == modifiedDateTo) ? defaultDateTo : modifiedDateTo;

        List<ReportOnSingleOfficeDto> reportData = officeService.getDataForReportBySingleOffice(idOfOffice, modifiedDateFrom, modifiedDateTo);
        if (reportData.isEmpty()) {
            return utils.getSendMessage(user.getChatId(), "Sorry, but for the period from  "
                    + modifiedDateFrom
                    + " to "
                    + modifiedDateTo
                    + " there is no any information. You can try again, choosing another period of time. \uD83E\uDDF8");
        } else {
            java.util.Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            parameters.put("modifiedDateFrom", dateFormat.format(modifiedDateFrom));
            parameters.put("modifiedDateTo", dateFormat.format(modifiedDateTo));
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_SINGLE_OFFICE_REPORT, parameters,
                    "ReportBySingleOffice".concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return utils.getSendMessage(user.getChatId(), "There is your report on office, you are welcome! Have a nice day to you \uD83C\uDF1D\uD83C\uDF1D\uD83C\uDF1D");
        }
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportOnAllOffices(User user, Date modifiedDateFrom, Date modifiedDateTo) {
        modifiedDateFrom = (null == modifiedDateFrom) ? defaultDateFrom : modifiedDateFrom;
        modifiedDateTo = (null == modifiedDateTo) ? defaultDateTo : modifiedDateTo;

        List<ReportOnAllOfficesDto> reportData = officeService.getDataForReportByAllOffices(modifiedDateFrom, modifiedDateTo);
        if (reportData.isEmpty()) {
            return utils.getSendMessage(user.getChatId(), "Sorry, but for the period from  "
                    + modifiedDateFrom
                    + " to "
                    + modifiedDateTo
                    + " there is no any information. You can try again, choosing another period of time. \uD83E\uDDF8");
        } else {
            java.util.Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            parameters.put("modifiedDateFrom", dateFormat.format(modifiedDateFrom));
            parameters.put("modifiedDateTo", dateFormat.format(modifiedDateTo));
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_ALL_OFFICES_REPORT, parameters,
                    "ReportOnAllOffices".concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return utils.getSendMessage(user.getChatId(), "There is your report on all offices, you are welcome! Have a nice day to you \uD83C\uDF1D\uD83C\uDF1D\uD83C\uDF1D");
        }
    }


    @SneakyThrows
    @Override
    public SendMessage sendReportOnEmployees(User user, Date dateFrom, Date dateTo) {
        dateFrom = (null == dateFrom) ? defaultDateFrom : dateFrom;
        dateTo = (null == dateTo) ? defaultDateTo : dateTo;

        List<ReportOnEmployeesDto> reportData = userService.getDataForReportByEmployees(dateFrom, dateTo);
        if (reportData.isEmpty()) {
            return utils.getSendMessage(user.getChatId(), "Sorry, but for the period from  "
                    + dateFrom
                    + " to "
                    + dateTo
                    + " there is no any information. You can try again, choosing another period of time. \uD83E\uDDF8");
        } else {
            java.util.Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            parameters.put("userCreationDateFrom", dateFormat.format(dateFrom));
            parameters.put("userCreationDateTo", dateFormat.format(dateTo));
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_EMPLOYEES_REPORT, parameters,
                    "ReportByEmployees".concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return utils.getSendMessage(user.getChatId(), "There is your report on employees, you are welcome! Have a nice day to you \uD83C\uDF1D\uD83C\uDF1D\uD83C\uDF1D");
        }
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportOnCity(User user, Long idOfCity, Date bookedDateFrom, Date bookedDateTo) {
        bookedDateFrom = (null == bookedDateFrom) ? defaultDateFrom : bookedDateFrom;
        bookedDateTo = (null == bookedDateTo) ? defaultDateTo : bookedDateTo;

        List<ReportOnCityDto> reportData = cityService.getDataForReportOnCity(idOfCity, bookedDateFrom, bookedDateTo);
        if (reportData.isEmpty()) {
            return utils.getSendMessage(user.getChatId(), "Sorry, but for the period from  "
                    + bookedDateFrom
                    + " to "
                    + bookedDateTo
                    + " there is no any information. You can try again, choosing another period of time. \uD83E\uDDF8");
        } else {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            parameters.put("bookedDateFrom", dateFormat.format(bookedDateFrom));
            parameters.put("bookedDateTo", dateFormat.format(bookedDateTo));
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_CITY_REPORT, parameters,
                    "ReportOnCity".concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return utils.getSendMessage(user.getChatId(), "There is your report on city, you are welcome! Have a nice day to you \uD83C\uDF1D\uD83C\uDF1D\uD83C\uDF1D");
        }
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportOnFloor(User user, Long idOfFloor, Date bookedDateFrom, Date bookedDateTo) {
        bookedDateFrom = (null == bookedDateFrom) ? defaultDateFrom : bookedDateFrom;
        bookedDateTo = (null == bookedDateTo) ? defaultDateTo : bookedDateTo;

        List<ReportOnFloorDto> reportData = mapService.getDataForReportOnFloor(idOfFloor, bookedDateFrom, bookedDateTo);
        if (reportData.isEmpty()) {
            return utils.getSendMessage(user.getChatId(), "Sorry, but for the period from  "
                    + bookedDateFrom
                    + " to "
                    + bookedDateTo
                    + " there is no any information. You can try again, choosing another period of time. \uD83E\uDDF8");
        } else {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            parameters.put("idOfFloor", idOfFloor);
            parameters.put("bookedDateFrom", dateFormat.format(bookedDateFrom));
            parameters.put("bookedDateTo", dateFormat.format(bookedDateTo));
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_FLOOR_REPORT, parameters,
                    "ReportOnFloor".concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return utils.getSendMessage(user.getChatId(), "There is your report on floor, you are welcome! Have a nice day to you \uD83C\uDF1D\uD83C\uDF1D\uD83C\uDF1D");
        }
    }
}
