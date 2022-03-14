package com.exadel.sandbox.team2.service.impl;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.dto.report.ReportOnEmployeesDto;
import com.exadel.sandbox.team2.dto.report.ReportOnSingleOfficeDto;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.report.ReportService;
import com.exadel.sandbox.team2.serivce.service.OfficeService;
import com.exadel.sandbox.team2.serivce.service.UserService;
import com.exadel.sandbox.team2.service.TelegramFileService;
import com.exadel.sandbox.team2.service.service.TelegramReportService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TelegramReportServiceImpl implements TelegramReportService {
    private final TelegramUtils utils;
    private final TelegramFileService telegramFileService;
    private final ReportService reportService;
    private final UserService userService;
    private final OfficeService officeService;
    private final String JRXML_PATH_FOR_EMPLOYEES_REPORT = "users_pattern.jrxml";
    private final String JRXML_PATH_FOR_SINGLE_OFFICE_REPORT = "single_office_pattern.jrxml";
    private final String JRXML_PATH_FOR_ALL_OFFICES_REPORT = "all_offices_pattern.jrxml";

    private final Date defaultDateFrom = new GregorianCalendar(1990, Calendar.JANUARY, 1).getTime();
    private final Date defaultDateTo = new GregorianCalendar(2050, Calendar.DECEMBER, 1).getTime();

    @SneakyThrows
    @Override
    public SendMessage sendReportOnSingleOffice(String chatId, User user, Long idOfOffice, Date modifiedDateFrom, Date modifiedDateTo) {
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
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_SINGLE_OFFICE_REPORT, parameters,
                    "ReportBySingleOffice".concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return utils.getSendMessage(user.getChatId(), "There is your report, you are welcome! Have a nice day to you \uD83C\uDF1D\uD83C\uDF1D\uD83C\uDF1D");
        }
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportOnAllOffices(String chatId, User user, Date modifiedDateFrom, Date modifiedDateTo) {
        modifiedDateFrom = (null == modifiedDateFrom) ? defaultDateFrom : modifiedDateFrom;
        modifiedDateTo = (null == modifiedDateTo) ? defaultDateTo : modifiedDateTo;

        List<ReportOnEmployeesDto> reportData = userService.getDataForReportByEmployees(modifiedDateFrom, modifiedDateTo);
        if (reportData.isEmpty()) {
            return utils.getSendMessage(user.getChatId(), "Sorry, but for the period from  "
                    + modifiedDateFrom
                    + " to "
                    + modifiedDateTo
                    + " there is no any information. You can try again, choosing another period of time. \uD83E\uDDF8");
        } else {
            java.util.Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_ALL_OFFICES_REPORT, parameters,
                    "ReportByEmployees".concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return utils.getSendMessage(user.getChatId(), "There is your report, you are welcome! Have a nice day to you \uD83C\uDF1D\uD83C\uDF1D\uD83C\uDF1D");
        }
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportOnEmployees(String chatId, User user, Date dateFrom, Date dateTo) {
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
            String filePath = reportService.constructReport(reportData, JRXML_PATH_FOR_EMPLOYEES_REPORT, parameters,
                    "ReportByEmployees".concat("_")
                            .concat(user.getFirstName())
                            .concat(user.getLastName())
                            .concat("_")
                            .concat(user.getChatId()));
            telegramFileService.sendDocument(user.getChatId(), filePath);
            return utils.getSendMessage(user.getChatId(), "There is your report, you are welcome! Have a nice day to you \uD83C\uDF1D\uD83C\uDF1D\uD83C\uDF1D");
        }
    }
}
