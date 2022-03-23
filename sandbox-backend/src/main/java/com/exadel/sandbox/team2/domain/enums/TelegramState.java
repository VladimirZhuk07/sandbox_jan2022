package com.exadel.sandbox.team2.domain.enums;

import com.exadel.sandbox.team2.domain.User;

public enum TelegramState {
    GIVE_PHONE_NUMBER, MAIN_MENU, GET_ACCOUNT_INFO,
    GET_CONTACT, SETTINGS, UPDATE_PHONE_NUMBER,
    MENU, CHOOSE_COUNTRY, CHOOSE_CITY, SHOW_OFFICES_BY_CITY,
    SHOW_WORKPLACES_BY_OFFICE, ASSIGN_BOOKING_TYPE,
    ONE_DAY_SELECT_DATE, CONTINUOUS_SELECT_DATE, RECURRING_SELECT_WEEK_DAY,
    BOOK_ONE_DAY_WORKPLACE, CHOOSE_LANGUAGE, SET_LANGUAGE, SELECT_END_DATE,
    SHOW_OFFICES_CONTINUOUS, SHOW_WORKPLACES_CONTINUOUS, GET_USER_BOOKINGS,
    RECURRING_DEFINE_WEEKDAYS, RECURRING_DEFINE_WEEKS, RECURRING_ASSIGN_END_WEEKDAY,
    RECURRING_ASSIGN_START_WEEKDAY, RECURRING_SHOW_WORKPLACES, CANCEL_BOOKING, DELETE_USER_BOOKING, REPORT,
    ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED, CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED, RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED,
    ONE_DAY_IS_KITCHEN_NEED, CONTINUOUS_IS_KITCHEN_NEED, RECURRING_IS_KITCHEN_NEED, IS_CONFERENCE_HALL_NEED,
    IS_NEXT_TO_WINDOWS_NEED_BE, IS_PC_NEED_BE, IS_MONITOR_NEED_BE, IS_KEYBOARD_NEED_BE, IS_MOUSE_NEED_BE, IS_HEADSET_NEED_BE,
    FINISH_DEFINE_WORKPLACE_ATTRIBUTES, BACK_TO_IS_KITCHEN_NEED, BACK_FROM_SELECT_ONE_DAY_DATE, BACK_FROM_SELECT_CONTINUOUS_DATE,
    BACK_FROM_SELECT_RECURRING_DATE, USER_REPORT_DEFINE_BOOKING_FROM, USER_REPORT_DEFINE_BOOKING_TO, GET_USER_REPORT,
    ALL_USER_REPORT_DEFINE_CREATE_DATE_FROM, ALL_USER_REPORT_DEFINE_CREATE_DATE_TO, GET_ALL_USER_REPORT,
    CITY_REPORT_DEFINE_ID, CITY_REPORT_DEFINE_BOOKING_FROM, CITY_REPORT_DEFINE_BOOKING_TO, GET_CITY_REPORT,
    OFFICE_REPORT_DEFINE_ID, OFFICE_REPORT_DEFINE_BOOKING_FROM, OFFICE_REPORT_DEFINE_BOOKING_TO, GET_OFFICE_REPORT,
    ALL_OFFICE_REPORT_DEFINE_BOOKING_FROM, ALL_OFFICE_REPORT_DEFINE_BOOKING_TO, GET_ALL_OFFICE_REPORT,
    FLOOR_REPORT_DEFINE_FLOOR, FLOOR_REPORT_DEFINE_BOOKING_FROM, FLOOR_REPORT_DEFINE_BOOKING_TO, GET_FLOOR_REPORT,
    CHECK_PHONE_NUMBER, EDIT_INFORMATION, EDIT_FIRSTNAME, EDIT_LASTNAME, EDIT_PASSWORD, CHECK_EDIT_INFORMATION,
    CHECK_EDIT_FIRSTNAME, CHECK_EDIT_LASTNAME, CHECK_EDIT_PASSWORD;

    /**
     * * Пераходы дзеляцца на два выгляду.
     * 1) Callback кнопка і адпраўленне паведамленні і далей пераход у пэўны стан.
     * 2) калі карыстальнік піша опредеенное паведамленне ў выглядзе нумара тэлефона або айди.
     *
     * Transitions are divided into two types.
     * 1) Callback button and sending a message and then switching to a certain state.
     * 2) When the user writes a certain message in the form of a phone number or ID.
     *
     * Переходы делятся на два вида.
     * 1) Callback кнопка и отправление сообщения и дальше переход в определённое состояние.
     * 2) Когда пользователь пишет опредеённое сообщение в виде номера телефона или айди.
     *
     * @param command command.
     * @param user user entity
     * @return boolean result
     */
    public static boolean commandToTelegramState(String command, User user){
        boolean isEditMessage = false;
        TelegramState telegramState = null;
        switch (command){
            case "/start" -> telegramState = MAIN_MENU;
            case "PHONE" -> telegramState = UPDATE_PHONE_NUMBER;
            case "BOOK" -> telegramState = CHOOSE_COUNTRY;
            case "INFO" -> telegramState = GET_USER_BOOKINGS;
            case "\uD83D\uDDD2 Menu" -> telegramState = MENU;
            case "\uD83D\uDC64 Account" -> telegramState = GET_ACCOUNT_INFO;
            case "\uD83D\uDCD8 Contact" -> telegramState = GET_CONTACT;
            case "⚙️ Settings" -> telegramState = SETTINGS;
            case "One day" -> telegramState = ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED;
            case "Continuous" -> telegramState = CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED;
            case "Recurring" -> telegramState = RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED;
            case "LANGUAGE" -> telegramState = CHOOSE_LANGUAGE;
            case "CANCEL_BOOKING" -> telegramState = CANCEL_BOOKING;
            case "DEFINE_WORKPLACE_ATTRIBUTES" -> {
                if(user.getTelegramState() == ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = ONE_DAY_IS_KITCHEN_NEED;
                }else if(user.getTelegramState() == CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = CONTINUOUS_IS_KITCHEN_NEED;
                }else{
                    telegramState = RECURRING_IS_KITCHEN_NEED;
                }
            }
            case "NOT_DEFINE_WORKPLACE_ATTRIBUTES" ->{
                if(user.getTelegramState() == ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = ONE_DAY_SELECT_DATE;
                }else if(user.getTelegramState() == CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = CONTINUOUS_SELECT_DATE;
                }else{
                    telegramState = RECURRING_SELECT_WEEK_DAY;
                }
            }
            case "REPORT" -> telegramState = REPORT;
            case "USER_REPORT" -> telegramState = USER_REPORT_DEFINE_BOOKING_FROM;
            case "ALL_USER_REPORT" -> telegramState = ALL_USER_REPORT_DEFINE_CREATE_DATE_FROM;
            case "OFFICES_REPORT" -> telegramState = ALL_OFFICE_REPORT_DEFINE_BOOKING_FROM;
            case "CITY_REPORT" -> telegramState = CITY_REPORT_DEFINE_ID;
            case "SINGLE_OFFICE_REPORT" -> telegramState = OFFICE_REPORT_DEFINE_ID;
            case "FLOOR_REPORT" -> telegramState = FLOOR_REPORT_DEFINE_FLOOR;

            case "EDIT_INFORMATION" -> telegramState = EDIT_INFORMATION;
            case "EDIT_FIRSTNAME" -> telegramState = EDIT_FIRSTNAME;
            case "EDIT_LASTNAME" -> telegramState = EDIT_LASTNAME;
            case "EDIT_PASSWORD" -> telegramState = EDIT_PASSWORD;
        }
        if(telegramState == null){
            switch (user.getTelegramState()){
                case CHOOSE_COUNTRY -> telegramState = CHOOSE_CITY;
                case CHOOSE_CITY -> telegramState = ASSIGN_BOOKING_TYPE;
                case ONE_DAY_IS_KITCHEN_NEED, CONTINUOUS_IS_KITCHEN_NEED, RECURRING_IS_KITCHEN_NEED -> telegramState = IS_CONFERENCE_HALL_NEED;
                case IS_CONFERENCE_HALL_NEED -> telegramState = IS_NEXT_TO_WINDOWS_NEED_BE;
                case IS_NEXT_TO_WINDOWS_NEED_BE -> telegramState = IS_PC_NEED_BE;
                case IS_PC_NEED_BE -> telegramState = IS_MONITOR_NEED_BE;
                case IS_MONITOR_NEED_BE -> telegramState = IS_KEYBOARD_NEED_BE;
                case IS_KEYBOARD_NEED_BE -> telegramState = IS_MOUSE_NEED_BE;
                case IS_MOUSE_NEED_BE -> telegramState = IS_HEADSET_NEED_BE;
                case IS_HEADSET_NEED_BE -> telegramState = FINISH_DEFINE_WORKPLACE_ATTRIBUTES;
                case ONE_DAY_SELECT_DATE -> telegramState = SHOW_OFFICES_BY_CITY;
                case CONTINUOUS_SELECT_DATE -> telegramState = SELECT_END_DATE;
                case RECURRING_SELECT_WEEK_DAY -> telegramState = RECURRING_DEFINE_WEEKDAYS;
                case RECURRING_DEFINE_WEEKDAYS -> telegramState = RECURRING_DEFINE_WEEKS;
                case RECURRING_DEFINE_WEEKS -> telegramState = RECURRING_ASSIGN_START_WEEKDAY;
                case RECURRING_ASSIGN_START_WEEKDAY -> telegramState = RECURRING_ASSIGN_END_WEEKDAY;
                case RECURRING_ASSIGN_END_WEEKDAY -> telegramState = RECURRING_SHOW_WORKPLACES;
                case SELECT_END_DATE -> telegramState = SHOW_OFFICES_CONTINUOUS;
                case SHOW_OFFICES_BY_CITY -> telegramState = SHOW_WORKPLACES_BY_OFFICE;
                case SHOW_OFFICES_CONTINUOUS -> telegramState = SHOW_WORKPLACES_CONTINUOUS;
                case SHOW_WORKPLACES_BY_OFFICE, SHOW_WORKPLACES_CONTINUOUS, RECURRING_SHOW_WORKPLACES -> telegramState = BOOK_ONE_DAY_WORKPLACE;
                case CHOOSE_LANGUAGE -> telegramState = SET_LANGUAGE;
                case CANCEL_BOOKING -> telegramState = DELETE_USER_BOOKING;
                case USER_REPORT_DEFINE_BOOKING_FROM -> telegramState = USER_REPORT_DEFINE_BOOKING_TO;
                case USER_REPORT_DEFINE_BOOKING_TO -> telegramState = GET_USER_REPORT;
                case ALL_USER_REPORT_DEFINE_CREATE_DATE_FROM -> telegramState = ALL_USER_REPORT_DEFINE_CREATE_DATE_TO;
                case ALL_USER_REPORT_DEFINE_CREATE_DATE_TO -> telegramState = GET_ALL_USER_REPORT;
                case ALL_OFFICE_REPORT_DEFINE_BOOKING_FROM -> telegramState = ALL_OFFICE_REPORT_DEFINE_BOOKING_TO;
                case ALL_OFFICE_REPORT_DEFINE_BOOKING_TO -> telegramState = GET_ALL_OFFICE_REPORT;
                case CITY_REPORT_DEFINE_ID -> telegramState = CITY_REPORT_DEFINE_BOOKING_FROM;
                case CITY_REPORT_DEFINE_BOOKING_FROM -> telegramState = CITY_REPORT_DEFINE_BOOKING_TO;
                case CITY_REPORT_DEFINE_BOOKING_TO -> telegramState = GET_CITY_REPORT;
                case OFFICE_REPORT_DEFINE_ID -> telegramState = OFFICE_REPORT_DEFINE_BOOKING_FROM;
                case OFFICE_REPORT_DEFINE_BOOKING_FROM -> telegramState = OFFICE_REPORT_DEFINE_BOOKING_TO;
                case OFFICE_REPORT_DEFINE_BOOKING_TO -> telegramState = GET_OFFICE_REPORT;
                case FLOOR_REPORT_DEFINE_FLOOR -> telegramState = FLOOR_REPORT_DEFINE_BOOKING_FROM;
                case FLOOR_REPORT_DEFINE_BOOKING_FROM -> telegramState = FLOOR_REPORT_DEFINE_BOOKING_TO;
                case FLOOR_REPORT_DEFINE_BOOKING_TO -> telegramState = GET_FLOOR_REPORT;
                case UPDATE_PHONE_NUMBER -> telegramState = CHECK_PHONE_NUMBER;

                case EDIT_FIRSTNAME -> telegramState = CHECK_EDIT_FIRSTNAME;
                case EDIT_LASTNAME -> telegramState = CHECK_EDIT_LASTNAME;
                case EDIT_PASSWORD -> telegramState = CHECK_EDIT_PASSWORD;
            }
        }
        if(telegramState != null)
            user.setTelegramState(telegramState);

        return isEditMessage;
    }

    /**
     * Back menu management
     *
     * @param user for getting telegram state
     */
    public static void backAndUserState(User user){
        TelegramState telegramState;
        telegramState = switch (user.getTelegramState()){
            case MENU,GET_ACCOUNT_INFO,GET_CONTACT,SETTINGS -> MAIN_MENU;
            case CHOOSE_COUNTRY,GET_USER_BOOKINGS,BOOK_ONE_DAY_WORKPLACE,DELETE_USER_BOOKING -> MENU;
            case CHOOSE_CITY -> CHOOSE_COUNTRY;
            case ASSIGN_BOOKING_TYPE -> CHOOSE_CITY;
            case ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED, CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED, RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED -> ASSIGN_BOOKING_TYPE;
            case ONE_DAY_IS_KITCHEN_NEED -> ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED;
            case CONTINUOUS_IS_KITCHEN_NEED -> CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED;
            case RECURRING_IS_KITCHEN_NEED -> RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED;
            case IS_CONFERENCE_HALL_NEED -> BACK_TO_IS_KITCHEN_NEED;
            case IS_NEXT_TO_WINDOWS_NEED_BE -> IS_CONFERENCE_HALL_NEED;
            case IS_PC_NEED_BE -> IS_NEXT_TO_WINDOWS_NEED_BE;
            case IS_MONITOR_NEED_BE -> IS_PC_NEED_BE;
            case IS_KEYBOARD_NEED_BE -> IS_MONITOR_NEED_BE;
            case IS_MOUSE_NEED_BE -> IS_KEYBOARD_NEED_BE;
            case IS_HEADSET_NEED_BE -> IS_MOUSE_NEED_BE;
            case ONE_DAY_SELECT_DATE -> BACK_FROM_SELECT_ONE_DAY_DATE;
            case CONTINUOUS_SELECT_DATE -> BACK_FROM_SELECT_CONTINUOUS_DATE;
            case RECURRING_SELECT_WEEK_DAY -> BACK_FROM_SELECT_RECURRING_DATE;
            case SHOW_OFFICES_BY_CITY -> ONE_DAY_SELECT_DATE;
            case SHOW_WORKPLACES_BY_OFFICE -> SHOW_OFFICES_BY_CITY;
            case SELECT_END_DATE -> CONTINUOUS_SELECT_DATE;
            case SHOW_OFFICES_CONTINUOUS -> SELECT_END_DATE;
            case SHOW_WORKPLACES_CONTINUOUS -> SHOW_OFFICES_CONTINUOUS;
            case RECURRING_DEFINE_WEEKDAYS -> RECURRING_SELECT_WEEK_DAY;
            case RECURRING_DEFINE_WEEKS -> RECURRING_DEFINE_WEEKDAYS;
            case RECURRING_ASSIGN_START_WEEKDAY -> RECURRING_DEFINE_WEEKS;
            case RECURRING_ASSIGN_END_WEEKDAY -> RECURRING_ASSIGN_START_WEEKDAY;
            case RECURRING_SHOW_WORKPLACES -> RECURRING_ASSIGN_END_WEEKDAY;
            case REPORT, CHECK_PHONE_NUMBER, EDIT_INFORMATION, UPDATE_PHONE_NUMBER  -> SETTINGS;
            case USER_REPORT_DEFINE_BOOKING_FROM, ALL_USER_REPORT_DEFINE_CREATE_DATE_FROM, FLOOR_REPORT_DEFINE_FLOOR, CITY_REPORT_DEFINE_ID, OFFICE_REPORT_DEFINE_ID -> REPORT;
            case FLOOR_REPORT_DEFINE_BOOKING_FROM -> FLOOR_REPORT_DEFINE_FLOOR;
            case CITY_REPORT_DEFINE_BOOKING_FROM -> CITY_REPORT_DEFINE_ID;
            case OFFICE_REPORT_DEFINE_BOOKING_FROM -> OFFICE_REPORT_DEFINE_ID;
            case FLOOR_REPORT_DEFINE_BOOKING_TO -> FLOOR_REPORT_DEFINE_BOOKING_FROM;
            case CITY_REPORT_DEFINE_BOOKING_TO -> CITY_REPORT_DEFINE_BOOKING_FROM;
            case OFFICE_REPORT_DEFINE_BOOKING_TO -> OFFICE_REPORT_DEFINE_BOOKING_FROM;
            case USER_REPORT_DEFINE_BOOKING_TO -> USER_REPORT_DEFINE_BOOKING_FROM;
            case ALL_USER_REPORT_DEFINE_CREATE_DATE_TO -> ALL_USER_REPORT_DEFINE_CREATE_DATE_FROM;
            case EDIT_FIRSTNAME, EDIT_LASTNAME, EDIT_PASSWORD,
                    CHECK_EDIT_FIRSTNAME, CHECK_EDIT_LASTNAME, CHECK_EDIT_PASSWORD -> EDIT_INFORMATION;
            default -> null;
        };
        if(telegramState != null)
            user.setTelegramState(telegramState);
    }
}
