package com.exadel.sandbox.team2.service.impl;

import com.exadel.sandbox.team2.domain.*;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
import com.exadel.sandbox.team2.dto.telegramDto.CreateBookingDto;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.serivce.service.*;
import com.exadel.sandbox.team2.service.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
        List<City> list;
        if(!countryName.equals("Back")) {
            CreateBookingDto dto = new CreateBookingDto();
            dto.setCountryName(countryName);
            list = cityService.findByCountryName(countryName);
            bookingList.put(chatId, dto);
        }else{
            CreateBookingDto dto = bookingList.get(chatId);
            list = cityService.findByCountryName(dto.getCountryName());
        }
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
    public SendMessage setBookingType(String chatId, String message, String[][] titles, String[][] commands, String city) {
        if(!city.equals("Back")) {
            CreateBookingDto dto = bookingList.get(chatId);
            dto.setCityName(city);
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage defineRecurringWeekdays(String chatId, String message, String weekdays, String[][] titles, String[][] commands, User user) {
        if(!weekdays.equals("Back")) {
            CreateBookingDto dto = bookingList.get(chatId);
            String[] list = weekdays.split(",");
            int cnt = 0;
            StringBuilder builder = new StringBuilder();
            builder.append(message);
            for (String weekday : list) {
                if (!checkWeekday(weekday, dto) || cnt == 7) {
                    user.setTelegramState(TelegramState.RECURRING_SELECT_WEEK_DAY);
                    return utils.getSendMessage(chatId, "Wrong format or entered weekdays more than 7");
                }
                builder.append(weekday).append(", ");
                cnt++;
            }
            builder.append(" right?\nIf not please press Back\nHow many weeks you want to book?");
            bookingList.put(chatId, dto);
            return utils.getSendMessage(chatId, builder.toString(), titles, commands);
        }
        return utils.getSendMessage(chatId, "Please enter how many weeks you want to book?", titles, commands);
    }

    @Override
    public SendMessage setEndDateForContinuousBooking(String chatId, String message, String startDate, User user, String[][] titles, String[][] commands) {
        CreateBookingDto dto = bookingList.get(chatId);
        if(!startDate.equals("Back")) {
            LocalDate localDate = checkDateAndSet(startDate, null, dto);
            if (localDate == null) {
                user.setTelegramState(TelegramState.CONTINUOUS_SELECT_DATE);
                return utils.getSendMessage(chatId, "Wrong form of date or date is expired, please write date in form of `2022-03-10`");
            }
            dto.setStartDate(localDate);
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage defineRecurringWeeks(String chatId, String message, String weekTimes, String[][] titles, String[][] commands) {
        if(!weekTimes.equals("Back")) {
            CreateBookingDto dto = bookingList.get(chatId);
            dto.setWeekTimes(Integer.parseInt(weekTimes));
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage defineRecurringStartDate(String chatId, String message, String startDate, User user, String[][] titles, String[][] commands) {
        if(!startDate.equals("Back")) {
            CreateBookingDto dto = bookingList.get(chatId);
            LocalDate localDate = checkDateAndSet(startDate, null, dto);
            if (localDate == null) {
                user.setTelegramState(TelegramState.RECURRING_DEFINE_WEEKS);
                return utils.getSendMessage(chatId, "Wrong form of date or date is expired, please write date in form of `2022-03-10`", titles, commands);
            }
            localDate = checkRecurringStartDate(localDate, dto);
            dto.setStartDate(localDate);
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage getOfficesByCity(String chatId, String message, String city, String[][] titles, String[][] commands) {
        List<Office> list = officeService.findByCityName(city);
        StringBuilder builder = new StringBuilder();
        builder.append(message);
        for(Office office: list){
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
        return utils.getSendMessage(chatId, builder.toString(), titles, commands);
    }

    @Override
    public SendMessage getOfficesByCityForOneDay(String chatId, String message, String date, User user, String[][] titles, String[][] commands) {
        CreateBookingDto dto = bookingList.get(chatId);
        if(!date.equals("Back")) {
            LocalDate startDate = checkDateAndSet(date, null, dto);
            if (startDate == null) {
                user.setTelegramState(TelegramState.ONE_DAY_SELECT_DATE);
                return utils.getSendMessage(chatId, "Wrong form of date or date is expired, please write date in form of `2022-03-10`");
            }
            dto.setStartDate(startDate);
            dto.setEndDate(startDate.plusDays(1));
            setWeekDays(startDate, dto.getEndDate(), dto, null);
            bookingList.put(chatId, dto);
        }

        return getOfficesByCity(chatId, message, dto.getCityName(), titles, commands);
    }

    @Override
    public SendMessage getOfficesByCityForContinuous(String chatId, String message, String date, User user, String[][] titles, String[][] commands) {
        CreateBookingDto dto = bookingList.get(chatId);
        if(!date.equals("Back")) {
            LocalDate endDate = checkDateAndSet(null, date, dto);
            if (endDate == null) {
                user.setTelegramState(TelegramState.SELECT_END_DATE);
                return utils.getSendMessage(chatId, "Wrong form of date or date is expired, please write date in form of `2022-03-10`");
            }
            dto.setEndDate(endDate);
            setWeekDays(dto.getStartDate(), endDate, dto, null);
            bookingList.put(chatId, dto);
        }

        return getOfficesByCity(chatId, message, dto.getCityName(), titles, commands);
    }

    @Override
    public SendMessage showRecurringOffices(String chatId, String message, String endWeekday, String[][] titles, String[][] commands, User user) {
        CreateBookingDto dto = bookingList.get(chatId);
        if(!endWeekday.equals("Back")) {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(endWeekday);
            if(dayOfWeek == null){
                user.setTelegramState(TelegramState.RECURRING_ASSIGN_START_WEEKDAY);
                return utils.getSendMessage(chatId, "Wrong format", titles, commands);
            }
            dayOfWeek = checkAndCorrectEndWeekday(dayOfWeek, dto);
            int cnt = 0;
            LocalDate date = dto.getStartDate();
            while (cnt < dto.getWeekTimes() && date.isBefore(date.plusMonths(2))) {
                date = date.with(TemporalAdjusters.next(dayOfWeek));
                cnt++;
            }
            dto.setEndDate(date);
            dto.setRecurring(true);
            bookingList.put(chatId, dto);
        }
        return getOfficesByCity(chatId, message, dto.getCityName(), titles, commands);
    }

    public SendMessage getWorkplaceByMapId(String chatId, String message, String officeId, User user, String[][] titles, String[][] commands){
        Map map = mapService.findByOfficeId(Long.valueOf(officeId));
        if(map == null){
            return utils.getSendMessage(chatId, "Wrong id");
        }
        CreateBookingDto dto = bookingList.get(chatId);
        List<Workplace> list = workplaceService.findByMapIdAndNotStartDate(map.getId(), dto);
        return showWorkplaces(chatId, message, map, list, titles, commands);
    }

    @Override
    public SendMessage bookWorkplace(String chatId, String message, String workplaceId, User user, String[][] titles, String[][] commands) {
        CreateBookingDto dto = bookingList.get(chatId);
        dto.setWorkplaceId(Long.parseLong(workplaceId));
        boolean isSaved = bookingService.save(dto, user);
        if(!isSaved){
            user.setTelegramState(TelegramState.SHOW_WORKPLACES_BY_OFFICE);
            return utils.getSendMessage(chatId, "Wrong id");
        }
        bookingList.remove(chatId);
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage getUserBookings(String chatId, String message, Long userId, String[][] titles, String[][] commands) {
        List<Booking> list = bookingService.getBookingByUserId(userId);
        StringBuilder builder = new StringBuilder();
        builder.append(message);
        for(Booking booking: list){
            builder.append(String.format("""
                \n==================================
                Id: %s
                Start date: %s
                End date: %s
                Recurring: %s
                Created date: %s
                """,
                    booking.getId(), booking.getStartDate(), booking.getEndDate(), booking.getRecurring(), booking.getCreatedDate()));
        }
        return utils.getSendMessage(chatId, builder.toString(), titles, commands);
    }

    @Override
    public SendMessage deleteUserBooking(String chatId, String message, String bookingId, User user, String[][] titles, String[][] commands) {
        if(!bookingService.deleteBooking(Long.valueOf(bookingId), user.getId())){
            user.setTelegramState(TelegramState.CANCEL_BOOKING);
            return utils.getSendMessage(chatId, "This id does not exist or not belong to you");
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    private LocalDate checkDateAndSet(String date, String endDate, CreateBookingDto dto){
        if(endDate != null)
            date = endDate;
        if(date.length() != 10 || !date.contains("-")){
            return null;
        }
        LocalDate localDate = LocalDate.parse(date);
        if(localDate.isBefore(LocalDate.now()) || localDate.isEqual(LocalDate.now())){
            return null;
        }
        if(localDate.isAfter(localDate.plusMonths(2)))
            return null;
        if(endDate != null && dto.getStartDate() != null){
            if(!localDate.isAfter(dto.getStartDate()))
                return null;
        }
        return localDate;
    }

    private SendMessage showWorkplaces(String chatId, String message, Map map, List<Workplace> list, String[][] titles, String[][] commands){
        StringBuilder builder = new StringBuilder();
        builder.append(message).append(String.format("""
                \nFloor number: %s
                Kitchen number: %s
                Conference number: %s
                """,
                map.getFloorNum(), map.getKitchenNum(), map.getConfRoomsNum()));
        for(Workplace workplace: list){
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
        return utils.getSendMessage(chatId, builder.toString(), titles, commands);
    }

    private void setWeekDays(LocalDate startDate, LocalDate endDate, CreateBookingDto dto, DayOfWeek weekDay){
        int cnt = 0;
        if(startDate != null && endDate != null) {
            while (!startDate.isEqual(endDate) && cnt != 7) {
                DayOfWeek dayOfWeek = startDate.getDayOfWeek();
                switch (dayOfWeek) {
                    case MONDAY -> dto.setMonday(true);
                    case TUESDAY -> dto.setTuesday(true);
                    case WEDNESDAY -> dto.setWednesday(true);
                    case THURSDAY -> dto.setThursday(true);
                    case FRIDAY -> dto.setFriday(true);
                    case SATURDAY -> dto.setSaturday(true);
                    case SUNDAY -> dto.setSunday(true);
                }
                startDate = startDate.plusDays(1);
                cnt++;
            }
        } else {
            switch (weekDay) {
                case MONDAY -> dto.setMonday(true);
                case TUESDAY -> dto.setTuesday(true);
                case WEDNESDAY -> dto.setWednesday(true);
                case THURSDAY -> dto.setThursday(true);
                case FRIDAY -> dto.setFriday(true);
                case SATURDAY -> dto.setSaturday(true);
                case SUNDAY -> dto.setSunday(true);
            }
        }
    }

    private boolean checkWeekday(String weekday, CreateBookingDto dto){
        switch (weekday){
            case "MONDAY" -> {
                dto.setMonday(true);
                return true;
            }
            case "TUESDAY" -> {
                dto.setTuesday(true);
                return true;
            }
            case "WEDNESDAY" -> {
                dto.setWednesday(true);
                return true;
            }
            case "THURSDAY" -> {
                dto.setThursday(true);
                return true;
            }
            case "FRIDAY" -> {
                dto.setFriday(true);
                return true;
            }
            case "SATURDAY" -> {
                dto.setSaturday(true);
                return true;
            }
            case "SUNDAY" -> {
                dto.setSunday(true);
                return true;
            }
        }
        return false;
    }

    private LocalDate checkRecurringStartDate(LocalDate date, CreateBookingDto dto){
        boolean isRight = false;
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        while (!isRight){
            switch (dayOfWeek){
                case MONDAY -> {
                    if(dto.getMonday() != null)
                        isRight = true;
                }
                case TUESDAY -> {
                    if(dto.getTuesday() != null)
                        isRight = true;
                }
                case WEDNESDAY -> {
                    if(dto.getWednesday() != null)
                        isRight = true;
                }
                case THURSDAY -> {
                    if(dto.getThursday() != null)
                        isRight = true;
                }
                case FRIDAY -> {
                    if(dto.getFriday() != null)
                        isRight = true;
                }
                case SATURDAY -> {
                    if(dto.getSaturday() != null)
                        isRight = true;
                }
                case SUNDAY -> {
                    if(dto.getSunday() != null)
                        isRight = true;
                }
            }
            if(!isRight) {
                date = date.plusDays(1);
                dayOfWeek = date.getDayOfWeek();
            }
        }
        return date;
    }

    private DayOfWeek checkAndCorrectEndWeekday(DayOfWeek dayOfWeek, CreateBookingDto dto){
        boolean isDone = false;
        while (true) {
            switch (dayOfWeek) {
                case MONDAY -> {
                    if (dto.getMonday() != null) {
                        dto.setMonday(true);
                        return DayOfWeek.MONDAY;
                    }
                }
                case TUESDAY -> {
                    if (dto.getTuesday() != null) {
                        dto.setTuesday(true);
                        return DayOfWeek.TUESDAY;
                    }
                }
                case WEDNESDAY -> {
                    if (dto.getWednesday() != null) {
                        dto.setWednesday(true);
                        return DayOfWeek.WEDNESDAY;
                    }
                }
                case THURSDAY -> {
                    if (dto.getThursday() != null) {
                        dto.setThursday(true);
                        return DayOfWeek.THURSDAY;
                    }
                }
                case FRIDAY -> {
                    if (dto.getFriday() != null) {
                        dto.setFriday(true);
                        return DayOfWeek.FRIDAY;
                    }
                }
                case SATURDAY -> {
                    if (dto.getSaturday() != null) {
                        dto.setSaturday(true);
                        return DayOfWeek.SATURDAY;
                    }
                }
                case SUNDAY -> {
                    if (dto.getSunday() != null) {
                        dto.setSunday(true);
                        return DayOfWeek.SUNDAY;
                    }
                }
            }
            if(!isDone){
                int value = dayOfWeek.getValue();
                if(value == 7)
                    value = 1;
                dayOfWeek = DayOfWeek.of(++value);
            }
        }
    }
}
