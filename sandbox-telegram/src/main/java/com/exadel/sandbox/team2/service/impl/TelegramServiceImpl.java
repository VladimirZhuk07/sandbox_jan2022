package com.exadel.sandbox.team2.service.impl;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.*;
import com.exadel.sandbox.team2.domain.enums.RoleType;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
import com.exadel.sandbox.team2.dto.MailDto;
import com.exadel.sandbox.team2.dto.telegram.CreateBookingDto;
import com.exadel.sandbox.team2.dto.telegram.CreatingReportDto;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.serivce.service.*;
import com.exadel.sandbox.team2.service.EmailService;
import com.exadel.sandbox.team2.service.LocaleMessageService;
import com.exadel.sandbox.team2.service.service.TelegramReportService;
import com.exadel.sandbox.team2.service.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private static final Hashtable<String, CreateBookingDto> bookingList = new Hashtable<>();
    private static final Hashtable<String, CreatingReportDto> reportList = new Hashtable<>();

    private final LocaleMessageService lms;
    private final TelegramUtils utils;
    private final CountryService countryService;
    private final CityService cityService;
    private final OfficeService officeService;
    private final MapService mapService;
    private final WorkplaceService workplaceService;
    private final BookingService bookingService;
    private final EmailService emailService;
    private final TelegramReportService telegramReportService;

    @Override
    public SendMessage getCountries(String chatId, String message, String data) {
        List<Country> list = countryService.findAll();
        List<List<String>> rows = new ArrayList<>();
        List<String> row = new ArrayList<>();
        for (Country value : list) {
            if (row.size() != 2) {
                row.add(value.getName());
            } else {
                rows.add(row);
                row = new ArrayList<>();
                row.add(value.getName());
            }
        }
        rows.add(row);
        rows.add(List.of("⬅ ️Back"));
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
            if (row.size() != 2) {
                row.add(value.getName());
            } else {
                rows.add(row);
                row = new ArrayList<>();
            }
        }
        rows.add(row);
        rows.add(List.of("⬅ ️Back"));
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
    public SendMessage isWorkplaceNeedBeDefine(String chatId, String message, String[][] titles, String[][] commands, String data) {
        if(data.equals("Back")){
            CreateBookingDto dto = bookingList.get(chatId);
            dto.setBookingTypeBeforeDefineWorkplaceAttributes(null);
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage isKitchenNeed(String chatId, String message, String[][] titles, String[][] commands, User user, String data) {
        if(!data.equals("Back")) {
            TelegramState telegramState = user.getTelegramState();
            CreateBookingDto dto = bookingList.get(chatId);
            if (telegramState == TelegramState.ONE_DAY_IS_KITCHEN_NEED) {
                dto.setBookingTypeBeforeDefineWorkplaceAttributes(TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED);
            } else if (telegramState == TelegramState.CONTINUOUS_IS_KITCHEN_NEED) {
                dto.setBookingTypeBeforeDefineWorkplaceAttributes(TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED);
            } else {
                dto.setBookingTypeBeforeDefineWorkplaceAttributes(TelegramState.RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED);
            }
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);

    }

    @Override
    public SendMessage backToIsKitchenNeed(String chatId, User user) {
        CreateBookingDto dto = bookingList.get(chatId);
        TelegramState telegramState = dto.getBookingTypeBeforeDefineWorkplaceAttributes();
        if (telegramState == TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED) {
            user.setTelegramState(TelegramState.ONE_DAY_IS_KITCHEN_NEED);
        } else if (telegramState == TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED) {
            user.setTelegramState(TelegramState.CONTINUOUS_IS_KITCHEN_NEED);
        } else {
            user.setTelegramState(TelegramState.RECURRING_IS_KITCHEN_NEED);
        }
        return utils.getSendMessage(chatId, "Should there be a kitchen?\nIf not, please press Next", new String[][]{{"Yes"},{"⬅ ️Back","Next ➡️️"}}, new String[][]{{"Yes"},{"Back","Next"}});
    }

    @Override
    public SendMessage isConferenceHallNeed(String chatId, String message, String[][] titles, String[][] commands, String data) {
        if(!data.equals("Back")){
            CreateBookingDto dto = bookingList.get(chatId);
            dto.setKitchenNum(1);
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage isNextToWindowNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data) {
        if(!data.equals("Back")){
            CreateBookingDto dto = bookingList.get(chatId);
            dto.setConfRoomsNum(1);
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage isPcNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data) {
        if(!data.equals("Back") && !data.equals("Next")){
            CreateBookingDto dto = bookingList.get(chatId);
            dto.setNextToWindow(!data.equals("No"));
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage isMonitorNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data) {
        if(!data.equals("Back") && !data.equals("Next")){
            CreateBookingDto dto = bookingList.get(chatId);
            dto.setPc(!data.equals("No"));
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage isKeyboardNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data) {
        if(!data.equals("Back") && !data.equals("Next")){
            CreateBookingDto dto = bookingList.get(chatId);
            dto.setMonitor(!data.equals("No"));
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage isMouseNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data) {
        if(!data.equals("Back") && !data.equals("Next")){
            CreateBookingDto dto = bookingList.get(chatId);
            dto.setKeyboard(!data.equals("No"));
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage isHeadsetNeedBe(String chatId, String message, String[][] titles, String[][] commands, String data) {
        if(!data.equals("Back") && !data.equals("Next")){
            CreateBookingDto dto = bookingList.get(chatId);
            dto.setMouse(!data.equals("No"));
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage finishDefineWorkplaceAttributes(String chatId, User user, String data) {
        CreateBookingDto dto = bookingList.get(chatId);
        if(!data.equals("Back") && !data.equals("Next")){
            dto.setMouse(!data.equals("No"));
            bookingList.put(chatId, dto);
        }
        TelegramState beforeWorkplace = dto.getBookingTypeBeforeDefineWorkplaceAttributes();
        if(beforeWorkplace == TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED){
            user.setTelegramState(TelegramState.ONE_DAY_SELECT_DATE);
            return utils.getSendMessage(chatId, "Please write start date of your booking in form of `2022-03-10`", new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
        }else if(beforeWorkplace == TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED){
            user.setTelegramState(TelegramState.CONTINUOUS_SELECT_DATE);
            return utils.getSendMessage(chatId, "Please write start date of your booking in form of `2022-03-10`", new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
        }else {
            user.setTelegramState(TelegramState.RECURRING_SELECT_WEEK_DAY);
            return utils.getSendMessage(chatId, "Please write all weekdays you want to book in the format 'MONDAY,TUESDAY,WEDNESDAY'", new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
        }
    }

    @Override
    public SendMessage backFromSelectDateOrSelectWeekDay(String chatId, User user, String date) {
        CreateBookingDto dto = bookingList.get(chatId);
        TelegramState beforeWorkplace = dto.getBookingTypeBeforeDefineWorkplaceAttributes();
        if(beforeWorkplace == null){
            TelegramState telegramState = user.getTelegramState();
            if(telegramState == TelegramState.BACK_FROM_SELECT_ONE_DAY_DATE){
                user.setTelegramState(TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED);
            }else if(telegramState == TelegramState.BACK_FROM_SELECT_CONTINUOUS_DATE){
                user.setTelegramState(TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED);
            }else {
                user.setTelegramState(TelegramState.RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED);
            }
            return utils.getSendMessage(chatId, "Do you want to define office/workplace attributes?", new String[][]{{"☑️Yes","❌ No"},{"⬅ ️Back"}}, new String[][]{{"DEFINE_WORKPLACE_ATTRIBUTES", "NOT_DEFINE_WORKPLACE_ATTRIBUTES"},{"Back"}});
        }else {
            user.setTelegramState(TelegramState.IS_HEADSET_NEED_BE);
            return isHeadsetNeedBe(chatId, "Should there be headset?\nIf no, then please press No.\nIf does not matter then press Next",
                    new String[][]{{"☑️Yes","❌ No"},{"⬅ ️Back","Next ➡️️"}}, new String[][]{{"Yes","No"},{"Back","Next"}}, date);
        }
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
                    return utils.getSendMessage(chatId, "Wrong format or entered weekdays more than 7", new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
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
                return utils.getSendMessage(chatId, "Wrong form of date or date is expired, please write date in form of `2022-03-10`",  new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
            }
            dto.setStartDate(localDate);
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage defineRecurringWeeks(String chatId, String message, String weekTimes, String[][] titles, String[][] commands, User user) {
        if(!weekTimes.equals("Back")) {
            CreateBookingDto dto = bookingList.get(chatId);
            int times = Integer.parseInt(weekTimes);
            if(times > 14){
                user.setTelegramState(TelegramState.RECURRING_DEFINE_WEEKDAYS);
                return utils.getSendMessage(chatId, "Please enter start date of your booking in the form of `2022-03-10`", new String[][] {{"⬅ ️Back"}}, new String[][] {{"Back"}});
            }
            dto.setWeekTimes(times);
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage defineRecurringStartDate(String chatId, String message, String startDate, User user, String[][] titles, String[][] commands) {
        if(!startDate.equals("Back")) {
            CreateBookingDto dto = bookingList.get(chatId);
            LocalDate localDate = checkDateAndSet(startDate, null, dto);
            if(localDate.plusWeeks(dto.getWeekTimes()).isAfter(LocalDate.now().plusMonths(3)))
                localDate = null;
            if (localDate == null) {
                user.setTelegramState(TelegramState.RECURRING_DEFINE_WEEKS);
                return utils.getSendMessage(chatId, "Wrong form of date or date is expired, or date exceeds 2 months, please write date in form of `2022-03-10`", titles, commands);
            }
            localDate = checkRecurringStartDate(localDate, dto);
            dto.setStartDate(localDate);
            bookingList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage getOfficesByCity(String chatId, String message, String city, String[][] titles, String[][] commands, CreateBookingDto dto) {
        List<Office> list;
        List<Long> officeIds = new ArrayList<>();
        if(dto == null){
            list = officeService.findAll();
        }else if(dto.getBookingTypeBeforeDefineWorkplaceAttributes() == null) {
            list = officeService.findByCityName(city);
        }else {
            list = officeService.findByParameters(dto.getKitchenNum(), dto.getConfRoomsNum(), dto.getCityName());
        }
        StringBuilder builder = new StringBuilder();
        builder.append(message);
        if(list.isEmpty()) {
            return utils.getSendMessage(chatId, "Sorry, but there is no offices", titles, commands);
        }
        for(Office office: list){
            officeIds.add(office.getId());
            String officeInfo = String.format("""
                            \n------------------------------
                            Id: %s
                            Name: %s
                            Address: %s
                            Parking: %s
                            """,
                    office.getId(), office.getName(), office.getAddress(), office.getParking());
            builder.append(officeInfo);
            assert dto != null;
            dto.setOfficeIds(officeIds);
            bookingList.put(chatId, dto);
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
                return utils.getSendMessage(chatId, "Wrong form of date or date is expired, please write date in form of `2022-03-10`", new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
            }
            dto.setStartDate(startDate);
            dto.setEndDate(startDate.plusDays(1));
            setWeekDays(startDate, dto.getEndDate(), dto, null);
            bookingList.put(chatId, dto);
        }

        return getOfficesByCity(chatId, message, dto.getCityName(), titles, commands, dto);
    }

    @Override
    public SendMessage getOfficesByCityForContinuous(String chatId, String message, String date, User user, String[][] titles, String[][] commands) {
        CreateBookingDto dto = bookingList.get(chatId);
        if(!date.equals("Back")) {
            LocalDate endDate = checkDateAndSet(null, date, dto);
            if (endDate == null) {
                user.setTelegramState(TelegramState.SELECT_END_DATE);
                return utils.getSendMessage(chatId, "Wrong form of date or date is expired, please write date in form of `2022-03-10`", new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
            }
            dto.setEndDate(endDate);
            setWeekDays(dto.getStartDate(), endDate, dto, null);
            bookingList.put(chatId, dto);
        }

        return getOfficesByCity(chatId, message, dto.getCityName(), titles, commands, dto);
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
        return getOfficesByCity(chatId, message, dto.getCityName(), titles, commands, dto);
    }

    public SendMessage getWorkplaceByMapId(String chatId, String message, String officeId, User user, String[][] titles, String[][] commands){
        CreateBookingDto dto = bookingList.get(chatId);
        Long officeId1 = Long.valueOf(officeId);
        List<Long> collect = dto.getOfficeIds().stream().filter(id -> (id.equals(officeId1))).toList();
        if(collect.size() == 0)
            return utils.getSendMessage(chatId, "This id was not in the list above or it does not exist, please enter right id",  new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
        Map map = mapService.findByOfficeId(officeId1);
        if(map == null){
            return utils.getSendMessage(chatId, "Wrong id");
        }
        Office office = officeService.findById(Long.valueOf(officeId)).orElse(null);
        assert office != null;
        dto.setOfficeName(office.getName());
        dto.setOfficeAddress(office.getAddress());
        List<Workplace> list = workplaceService.findByMapIdAndNotStartDate(map.getId(), dto);
        if(list.isEmpty())
            return utils.getSendMessage(chatId, "Sorry, but there is no any workplaces", titles, commands);
        return showWorkplaces(chatId, message, map, list, titles, commands, dto);
    }

    @Override
    public SendMessage bookWorkplace(String chatId, String message, String workplaceId, User user, String[][] titles, String[][] commands) {
        CreateBookingDto dto = bookingList.get(chatId);
        long workplaceId1 = Long.parseLong(workplaceId);
        List<Long> list = dto.getWorkplaceIds().stream().filter(id -> (id.equals(workplaceId1))).toList();
        if(list.size() == 0)
            return utils.getSendMessage(chatId, "This id was not in the list above or it does not exist, please enter right id", new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});

        dto.setWorkplaceId(workplaceId1);
        boolean isSaved = bookingService.save(dto, user);
        if(!isSaved){
            user.setTelegramState(TelegramState.SHOW_WORKPLACES_BY_OFFICE);
            return utils.getSendMessage(chatId, "Wrong id");
        }
        MailDto mailDto = new MailDto();
        mailDto.setRecipient(user.getEmail());
        mailDto.setHeader("Your booking is approved");
        mailDto.putTextForBooking(dto.getCountryName(), dto.getCityName(), dto.getOfficeName(), dto.getOfficeAddress(), 1);
        emailService.sendMail(mailDto);
        bookingList.remove(chatId);
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage getUserBookings(String chatId, String message, Long userId, String[][] titles, String[][] commands) {
        List<Booking> list = bookingService.getBookingByUserId(userId);
        StringBuilder builder = new StringBuilder();
        builder.append(message);
        if(list.isEmpty())
            return utils.getSendMessage(chatId, "You don`t have bookings yet", new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
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
            user.setTelegramState(TelegramState.GET_USER_BOOKINGS);
            return utils.getSendMessage(chatId, "This id does not exist or not belong to you", new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
        }
        return utils.getSendMessage(chatId, message, titles, commands);
    }

    @Override
    public SendMessage checkUserRole(String chatId, String message, String[][] titles, String[][] commands, User user) {
        Set<Role> list = user.getRoles();
        for(Role role: list){
            if(role.getName().equals(RoleType.ADMIN) || role.getName().equals(RoleType.MANAGER) || role.getName().equals(RoleType.SYSTEM)){
                return utils.getSendMessage(chatId, message, titles, commands);
            }
        }

        user.setTelegramState(TelegramState.SETTINGS);
        return utils.getSendMessage(chatId, "Sorry, but users are not allowed to use this functions\nPlease select action", new String[][]{{lms.getMessage("settings.changePhoneNumber"), lms.getMessage("settings.editAccountInformation")},
                        {lms.getMessage("settings.changeLanguage"), lms.getMessage("settings.report")}, {"⬅ ️Back"}},
                new String[][]{{"PHONE", "INFORMATION"}, {"LANGUAGE", "REPORT"}, {"Back"}});
    }

    @Override
    public SendMessage defineId(String chatId, String message, String id, User user) {
        if(!id.equals("Back")) {
            CreatingReportDto dto = new CreatingReportDto();
            if(user.getTelegramState() == TelegramState.CITY_REPORT_DEFINE_BOOKING_FROM){
                Optional<City> byName = cityService.findByName(id);
                if(byName.isEmpty()){
                    user.setTelegramState(TelegramState.CITY_REPORT_DEFINE_ID);
                    return utils.getSendMessage(chatId, "This city does not exist", new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
                }
                dto.setId(byName.get().getId());
            }else{
                dto.setId(Long.valueOf(id));
            }
            reportList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, new String[][] {{"Back to Menu"}}, new String[][] {{"Back"}});
    }

    @Override
    public SendMessage defineDateFrom(String chatId, String message, String errorMessage, TelegramState telegramState, String dateFrom, User user) {
        if(!dateFrom.equals("Back")){
            CreatingReportDto dto;
            if(user.getTelegramState() == TelegramState.USER_REPORT_DEFINE_BOOKING_TO
            || user.getTelegramState() == TelegramState.ALL_USER_REPORT_DEFINE_CREATE_DATE_TO
            || user.getTelegramState() == TelegramState.ALL_OFFICE_REPORT_DEFINE_BOOKING_TO) {
                dto = new CreatingReportDto();
            }else{
                dto = reportList.get(chatId);
            }
            Date date = checkDateForReport(dateFrom, null);
            if(date == null){
                user.setTelegramState(telegramState);
                return utils.getSendMessage(chatId, errorMessage, new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
            }
            dto.setBookingFrom(date);
            reportList.put(chatId, dto);
        }
        return utils.getSendMessage(chatId, message, new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
    }

    @Override
    public SendMessage getReport(String chatId, String dateTo, User user, TelegramState telegramState, String message) {
        CreatingReportDto dto = reportList.get(chatId);
        Date date = checkDateForReport(dateTo, dto);
        if(date == null){
            user.setTelegramState(telegramState);
            return utils.getSendMessage(chatId, message, new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
        }
        reportList.remove(chatId);
        return switch (user.getTelegramState()){
            case GET_USER_REPORT -> telegramReportService.sendReportOnUsers(user, dto.getBookingFrom(), date);
            case GET_ALL_USER_REPORT -> telegramReportService.sendReportOnEmployees(user, dto.getBookingFrom(), date);
            case GET_ALL_OFFICE_REPORT -> telegramReportService.sendReportOnAllOffices(user, dto.getBookingFrom(), date);
            case GET_CITY_REPORT -> telegramReportService.sendReportOnCity(user, dto.getId(), dto.getBookingFrom(), date);
            case GET_FLOOR_REPORT -> telegramReportService.sendReportOnFloor(user, dto.getId(), dto.getBookingFrom(), date);
            case GET_OFFICE_REPORT -> telegramReportService.sendReportOnSingleOffice(user, dto.getId(), dto.getBookingFrom(), date);
            default -> utils.getSendMessage(chatId, "ERROR‼️");
        };
    }

    @Override
    public SendMessage changePhoneNumber(String chatId, String newPhoneNumber, User user) {
        while (newPhoneNumber.length() < 7) {
            user.setTelegramState(TelegramState.UPDATE_PHONE_NUMBER);
            return utils.getSendMessage(chatId, "Please enter right number of phone!\nIn format: +[code of country] [code of operator] [numbers of phone]"
                    ,new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
        }
        newPhoneNumber = newPhoneNumber.replaceAll(" ","");
        user.setPhoneNumber(newPhoneNumber);
        return utils.getSendMessage(chatId, "Your phone number is updated successfully!"
                , new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
    }

    @Override
    public SendMessage editAccountInformation(String chatId, String newData, User user) {
        SendMessage sendMessage;
        switch (user.getTelegramState()) {
            case CHECK_EDIT_FIRSTNAME -> {
                user.setFirstName(newData);
                sendMessage = utils.getSendMessage(chatId, "Your firstname is changed successfully!"
                        ,new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
            }
            case CHECK_EDIT_LASTNAME -> {
                user.setLastName(newData);
                sendMessage = utils.getSendMessage(chatId, "Your last name changed successfully!"
                        ,new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
            }
            case CHECK_EDIT_PASSWORD -> {
                user.setPassword(newData);
                sendMessage = utils.getSendMessage(chatId, "Your password is changed successfully!"
                        ,new String[][]{{"⬅ ️Back"}}, new String[][]{{"Back"}});
            }
            default -> sendMessage = utils.getSendMessage(chatId, "Error editing information");
        }
        return sendMessage;
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
        if(localDate.isAfter(LocalDate.now().plusMonths(3)))
            return null;
        if(endDate != null && dto.getStartDate() != null){
            if(!localDate.isAfter(dto.getStartDate()))
                return null;
        }
        return localDate;
    }

    private SendMessage showWorkplaces(String chatId, String message, Map map, List<Workplace> list, String[][] titles, String[][] commands, CreateBookingDto dto){
        StringBuilder builder = new StringBuilder();
        List<Long> workplaceIds = new ArrayList<>();
        builder.append(message).append(String.format("""
                \nFloor number: %s
                Kitchen number: %s
                Conference number: %s
                """,
                map.getFloorNum(), map.getKitchenNum(), map.getConfRoomsNum()));
        for(Workplace workplace: list){
            workplaceIds.add(workplace.getId());
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
            dto.setWorkplaceIds(workplaceIds);
            bookingList.put(chatId, dto);
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

    private Date checkDateForReport(String date, CreatingReportDto dto){
        if(date.length() != 10 || !date.contains("-")){
            return null;
        }
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dto != null){
            if(dto.getBookingFrom().after(date1))
                return null;
        }
        return date1;
    }

}
