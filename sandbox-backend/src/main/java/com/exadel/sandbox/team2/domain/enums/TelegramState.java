package com.exadel.sandbox.team2.domain.enums;

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
    BACK_FROM_SELECT_RECURRING_DATE,USER_REPORT, ALL_USER_REPORT, CITY_REPORT, OFFICE_REPORT, ALL_OFFICE_REPORT, FLOOR_REPORT;


    public static boolean commandToTelegramState(String command, User user){
        boolean isEditMessage = false;
        TelegramState telegramState = null;
        switch (command){
            case "/start" -> telegramState = TelegramState.MAIN_MENU;
            case "PHONE" -> telegramState = TelegramState.UPDATE_PHONE_NUMBER;
            case "BOOK" -> telegramState = TelegramState.CHOOSE_COUNTRY;
            case "INFO" -> telegramState = TelegramState.GET_USER_BOOKINGS;
            case "\uD83D\uDDD2 Menu" -> telegramState = TelegramState.MENU;
            case "\uD83D\uDC64 Account" -> telegramState = TelegramState.GET_ACCOUNT_INFO;
            case "\uD83D\uDCD8 Contact" -> telegramState = TelegramState.GET_CONTACT;
            case "⚙️ Settings" -> telegramState = TelegramState.SETTINGS;
            case "One day" -> telegramState = TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED;
            case "Continuous" -> telegramState = TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED;
            case "Recurring" -> telegramState = TelegramState.RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED;
            case "LANGUAGE" -> telegramState = TelegramState.CHOOSE_LANGUAGE;
            case "CANCEL_BOOKING" -> telegramState = TelegramState.CANCEL_BOOKING;
            case "DEFINE_WORKPLACE_ATTRIBUTES" -> {
                if(user.getTelegramState() == TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = TelegramState.ONE_DAY_IS_KITCHEN_NEED;
                }else if(user.getTelegramState() == TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = TelegramState.CONTINUOUS_IS_KITCHEN_NEED;
                }else{
                    telegramState = TelegramState.RECURRING_IS_KITCHEN_NEED;
                }
            }
            case "NOT_DEFINE_WORKPLACE_ATTRIBUTES" ->{
                if(user.getTelegramState() == TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = TelegramState.ONE_DAY_SELECT_DATE;
                }else if(user.getTelegramState() == TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED){
                    telegramState = TelegramState.CONTINUOUS_SELECT_DATE;
                }else{
                    telegramState = TelegramState.RECURRING_SELECT_WEEK_DAY;
                }
            }
            case "REPORT" -> telegramState = TelegramState.REPORT;
        }
        if(telegramState == null){
            switch (user.getTelegramState()){
                case CHOOSE_COUNTRY -> telegramState = TelegramState.CHOOSE_CITY;
                case CHOOSE_CITY -> telegramState = TelegramState.ASSIGN_BOOKING_TYPE;
                case ONE_DAY_IS_KITCHEN_NEED, CONTINUOUS_IS_KITCHEN_NEED, RECURRING_IS_KITCHEN_NEED -> telegramState = TelegramState.IS_CONFERENCE_HALL_NEED;
                case IS_CONFERENCE_HALL_NEED -> telegramState = TelegramState.IS_NEXT_TO_WINDOWS_NEED_BE;
                case IS_NEXT_TO_WINDOWS_NEED_BE -> telegramState = TelegramState.IS_PC_NEED_BE;
                case IS_PC_NEED_BE -> telegramState = TelegramState.IS_MONITOR_NEED_BE;
                case IS_MONITOR_NEED_BE -> telegramState = TelegramState.IS_KEYBOARD_NEED_BE;
                case IS_KEYBOARD_NEED_BE -> telegramState = TelegramState.IS_MOUSE_NEED_BE;
                case IS_MOUSE_NEED_BE -> telegramState = TelegramState.IS_HEADSET_NEED_BE;
                case IS_HEADSET_NEED_BE -> telegramState = TelegramState.FINISH_DEFINE_WORKPLACE_ATTRIBUTES;
                case ONE_DAY_SELECT_DATE -> telegramState = TelegramState.SHOW_OFFICES_BY_CITY;
                case CONTINUOUS_SELECT_DATE -> telegramState = TelegramState.SELECT_END_DATE;
                case RECURRING_SELECT_WEEK_DAY -> telegramState = TelegramState.RECURRING_DEFINE_WEEKDAYS;
                case RECURRING_DEFINE_WEEKDAYS -> telegramState = TelegramState.RECURRING_DEFINE_WEEKS;
                case RECURRING_DEFINE_WEEKS -> telegramState = TelegramState.RECURRING_ASSIGN_START_WEEKDAY;
                case RECURRING_ASSIGN_START_WEEKDAY -> telegramState = TelegramState.RECURRING_ASSIGN_END_WEEKDAY;
                case RECURRING_ASSIGN_END_WEEKDAY -> telegramState = TelegramState.RECURRING_SHOW_WORKPLACES;
                case SELECT_END_DATE -> telegramState = TelegramState.SHOW_OFFICES_CONTINUOUS;
                case SHOW_OFFICES_BY_CITY -> telegramState = TelegramState.SHOW_WORKPLACES_BY_OFFICE;
                case SHOW_OFFICES_CONTINUOUS -> telegramState = TelegramState.SHOW_WORKPLACES_CONTINUOUS;
                case SHOW_WORKPLACES_BY_OFFICE, SHOW_WORKPLACES_CONTINUOUS, RECURRING_SHOW_WORKPLACES -> telegramState = TelegramState.BOOK_ONE_DAY_WORKPLACE;
                case CHOOSE_LANGUAGE -> telegramState = TelegramState.SET_LANGUAGE;
                case CANCEL_BOOKING -> telegramState = TelegramState.DELETE_USER_BOOKING;
            }
        }
        if(telegramState != null)
            user.setTelegramState(telegramState);

        return isEditMessage;
    }

    public static void backAndUserState(User user){
        TelegramState telegramState;
        telegramState = switch (user.getTelegramState()){
            case MENU,GET_ACCOUNT_INFO,GET_CONTACT,SETTINGS -> TelegramState.MAIN_MENU;
            case CHOOSE_COUNTRY,GET_USER_BOOKINGS,BOOK_ONE_DAY_WORKPLACE,DELETE_USER_BOOKING -> TelegramState.MENU;
            case CHOOSE_CITY -> TelegramState.CHOOSE_COUNTRY;
            case ASSIGN_BOOKING_TYPE -> TelegramState.CHOOSE_CITY;
            case ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED, CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED, RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED -> TelegramState.ASSIGN_BOOKING_TYPE;
            case ONE_DAY_IS_KITCHEN_NEED -> TelegramState.ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED;
            case CONTINUOUS_IS_KITCHEN_NEED -> TelegramState.CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED;
            case RECURRING_IS_KITCHEN_NEED -> TelegramState.RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED;
            case IS_CONFERENCE_HALL_NEED -> TelegramState.BACK_TO_IS_KITCHEN_NEED;
            case IS_NEXT_TO_WINDOWS_NEED_BE -> TelegramState.IS_CONFERENCE_HALL_NEED;
            case IS_PC_NEED_BE -> TelegramState.IS_NEXT_TO_WINDOWS_NEED_BE;
            case IS_MONITOR_NEED_BE -> TelegramState.IS_PC_NEED_BE;
            case IS_KEYBOARD_NEED_BE -> TelegramState.IS_MONITOR_NEED_BE;
            case IS_MOUSE_NEED_BE -> TelegramState.IS_KEYBOARD_NEED_BE;
            case IS_HEADSET_NEED_BE -> TelegramState.IS_MOUSE_NEED_BE;
            case ONE_DAY_SELECT_DATE -> TelegramState.BACK_FROM_SELECT_ONE_DAY_DATE;
            case CONTINUOUS_SELECT_DATE -> TelegramState.BACK_FROM_SELECT_CONTINUOUS_DATE;
            case RECURRING_SELECT_WEEK_DAY -> TelegramState.BACK_FROM_SELECT_RECURRING_DATE;
            case SHOW_OFFICES_BY_CITY -> TelegramState.ONE_DAY_SELECT_DATE;
            case SHOW_WORKPLACES_BY_OFFICE -> TelegramState.SHOW_OFFICES_BY_CITY;
            case SELECT_END_DATE -> TelegramState.CONTINUOUS_SELECT_DATE;
            case SHOW_OFFICES_CONTINUOUS -> TelegramState.SELECT_END_DATE;
            case SHOW_WORKPLACES_CONTINUOUS -> TelegramState.SHOW_OFFICES_CONTINUOUS;
            case RECURRING_DEFINE_WEEKDAYS -> TelegramState.RECURRING_SELECT_WEEK_DAY;
            case RECURRING_DEFINE_WEEKS -> TelegramState.RECURRING_DEFINE_WEEKDAYS;
            case RECURRING_ASSIGN_START_WEEKDAY -> TelegramState.RECURRING_DEFINE_WEEKS;
            case RECURRING_ASSIGN_END_WEEKDAY -> TelegramState.RECURRING_ASSIGN_START_WEEKDAY;
            case RECURRING_SHOW_WORKPLACES -> TelegramState.RECURRING_ASSIGN_END_WEEKDAY;
            default -> null;
        };
        if(telegramState != null)
            user.setTelegramState(telegramState);
    }
}
