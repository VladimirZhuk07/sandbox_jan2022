package com.exadel.sandbox.team2.service.impl;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.*;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
import com.exadel.sandbox.team2.dto.report.ReportByEmployeesDto;
import com.exadel.sandbox.team2.dto.telegramDto.CreateBookingDto;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.report.ReportService;
import com.exadel.sandbox.team2.serivce.service.*;
import com.exadel.sandbox.team2.service.TelegramFileService;
import com.exadel.sandbox.team2.service.service.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private static final Hashtable<String, CreateBookingDto> bookingList = new Hashtable<>();

    private final TelegramUtils utils;
    private final CountryService countryService;
    private final CityService cityService;
    private final OfficeService officeService;
    private final MapService mapService;
    private final WorkplaceService workplaceService;
    private final BookingService bookingService;
    private final TelegramFileService telegramFileService;
    private final UserService userService;
    private final ReportService reportService;

    @Override
    public SendMessage getCountries(String chatId, String message, String data) {
        List<Country> list = countryService.findAll();
        List<List<String>> rows = new ArrayList<>();
        List<String> row = new ArrayList<>();
        for (Country value : list) {
            if (row.size() != 3) {
                row.add(value.getName());
            } else {
                rows.add(row);
                row = new ArrayList<>();
            }
        }
        rows.add(row);
        rows.add(List.of("Back"));
        return utils.showOptions(chatId, message, rows);
    }

    @Override
    public SendMessage getCities(String chatId, String message, String countryName) {
        List<City> list = cityService.findByCountryName(countryName);
        List<List<String>> rows = new ArrayList<>();
        List<String> row = new ArrayList<>();
        for (City value : list) {
            if (row.size() != 3) {
                row.add(value.getName());
            } else {
                rows.add(row);
                row = new ArrayList<>();
            }
        }
        rows.add(row);
        rows.add(List.of("Back"));
        return utils.showOptions(chatId, message, rows);
    }

    @Override
    public SendMessage getOfficesByCity(String chatId, String message, String city) {
        List<Office> list = officeService.findByCityName(city);
        StringBuilder builder = new StringBuilder();
        builder.append(message);
        for (Office office : list) {
            String officeInfo = String.format("""
                            \n------------------------------
                            Id: %s
                            Name: %s
                            Address: %s
                            Parking: %s
                            """,
                    office.getId(), office.getName(), office.getAddress(), office.getParking());
            builder.append(officeInfo);
        }
        return utils.getSendMessage(chatId, builder.toString());
    }

    @Override
    public SendMessage getOfficesByCityForOneDay(String chatId, String message, String date, User user) {
        LocalDate startDate = checkDateAndSet(date);
        if (startDate == null) {
            user.setTelegramState(TelegramState.ONE_DAY_SELECT_DATE);
            return utils.getSendMessage(chatId, "Please write date in form of `2020-01-08`");
        }
        CreateBookingDto dto = bookingList.get(chatId);
        dto.setStartDate(startDate);
        bookingList.put(chatId, dto);

        return getOfficesByCity(chatId, message, dto.getCityName());
    }

    @Override
    public SendMessage setBookingType(String chatId, String message, String[][] titles, String[][] commands, String city) {
        CreateBookingDto dto = new CreateBookingDto();
        dto.setCityName(city);
        bookingList.put(chatId, dto);
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    public SendMessage getWorkplaceByMapId(String chatId, String message, String officeId) {
        Map map = mapService.findByOfficeId(Long.valueOf(officeId));
        if (map == null) {
            return utils.getSendMessage(chatId, "Wrong id");
        }
        CreateBookingDto dto = bookingList.get(chatId);
        List<Workplace> list = workplaceService.findByMapIdAndNotStartDate(map.getId(), dto.getStartDate());
        StringBuilder builder = new StringBuilder();
        builder.append(message).append(String.format("""
                        \nFloor number: %s
                        Kitchen number: %s
                        Conference number: %s
                        """,
                map.getFloorNum(), map.getKitchenNum(), map.getConfRoomsNum()));
        for (Workplace workplace : list) {
            String officeInfo = String.format("""
                            \n------------------------------
                            Id: %s
                            Workplace number: %s
                            Headset: %s
                            Keyboard: %s
                            Monitor: %s
                            Mouse: %s
                            PC: %s
                            Next to window: %s
                            """,
                    workplace.getId(), workplace.getWorkplaceNumber(), workplace.getHeadset(), workplace.getKeyboard(),
                    workplace.getMonitor(), workplace.getPc(), workplace.getMouse(), workplace.getNextToWindow());
            builder.append(officeInfo);
        }
        return utils.getSendMessage(chatId, builder.toString());
    }

    @Override
    public SendMessage bookOneDayWorkplace(String chatId, String message, String workplaceId, User user) {
        CreateBookingDto dto = bookingList.get(chatId);
        dto.setWorkplaceId(Long.parseLong(workplaceId));
        dto.setEndDate(dto.getStartDate().plusDays(1));
        boolean isSaved = bookingService.save(dto, user);
        if (!isSaved)
            return utils.getSendMessage(chatId, "Wrong id");
        bookingList.remove(chatId);
        return utils.getSendMessage(chatId, message);
    }

    private LocalDate checkDateAndSet(String date) {
        if (date.length() != 10 || !date.contains("-")) {
            return null;
        }
        LocalDate startDate = LocalDate.parse(date);
        if (startDate.isBefore(LocalDate.now()) || startDate.isEqual(LocalDate.now())) {
            return null;
        }
        return startDate;
    }

    @SneakyThrows
    @Override
    public SendMessage sendReportByEmployees(String chatId, User user, Date dateFrom, Date dateTo) {
        dateFrom = new GregorianCalendar(2021, Calendar.FEBRUARY, 11).getTime();
        dateTo = new GregorianCalendar(2025, Calendar.FEBRUARY, 11).getTime();
        List<ReportByEmployeesDto> reportData = userService.getDataForReportByEmployees(dateFrom, dateTo);
        if (reportData.isEmpty()) {
            return utils.getSendMessage(user.getChatId(), "Sorry, but for the period from  "
                    + dateFrom
                    + " to "
                    + dateTo
                    + " there is no any information. You can try again, choosing another period of time. \uD83E\uDDF8");
        } else {
            java.util.Map<String, Object> parameters = new HashMap<>();
            parameters.put("total", reportData.size());
            String filePath = reportService.constructReport(reportData, "users.jrxml", parameters,
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
