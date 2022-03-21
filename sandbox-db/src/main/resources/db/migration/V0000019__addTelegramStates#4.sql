ALTER TABLE `User`
    MODIFY telegramState        ENUM(
    'GIVE_PHONE_NUMBER', 'MAIN_MENU', 'GET_ACCOUNT_INFO',
    'GET_CONTACT', 'SETTINGS', 'UPDATE_PHONE_NUMBER',
    'MENU', 'CHOOSE_COUNTRY', 'CHOOSE_CITY', 'SHOW_OFFICES_BY_CITY',
    'SHOW_WORKPLACES_BY_OFFICE', 'ASSIGN_BOOKING_TYPE',
    'ONE_DAY_SELECT_DATE', 'CONTINUOUS_SELECT_DATE', 'RECURRING_SELECT_WEEK_DAY',
    'BOOK_ONE_DAY_WORKPLACE', 'CHOOSE_LANGUAGE', 'SET_LANGUAGE', 'SELECT_END_DATE',
    'SHOW_OFFICES_CONTINUOUS', 'SHOW_WORKPLACES_CONTINUOUS', 'GET_USER_BOOKINGS',
    'RECURRING_DEFINE_WEEKDAYS', 'RECURRING_ASSIGN_END_WEEKDAY', 'RECURRING_DEFINE_WEEKS',
    'RECURRING_ASSIGN_START_WEEKDAY', 'RECURRING_SHOW_WORKPLACES', 'CANCEL_BOOKING', 'DELETE_USER_BOOKING', 'REPORT',
    'ONE_DAY_IS_WORKPLACE_ATTRIBUTES_NEED', 'CONTINUOUS_IS_WORKPLACE_ATTRIBUTES_NEED', 'RECURRING_IS_WORKPLACE_ATTRIBUTES_NEED',
    'ONE_DAY_IS_KITCHEN_NEED', 'CONTINUOUS_IS_KITCHEN_NEED', 'RECURRING_IS_KITCHEN_NEED', 'IS_CONFERENCE_HALL_NEED',
    'IS_NEXT_TO_WINDOWS_NEED_BE', 'IS_PC_NEED_BE', 'IS_MONITOR_NEED_BE', 'IS_KEYBOARD_NEED_BE', 'IS_MOUSE_NEED_BE', 'IS_HEADSET_NEED_BE',
    'FINISH_DEFINE_WORKPLACE_ATTRIBUTES', 'BACK_TO_IS_KITCHEN_NEED', 'BACK_FROM_SELECT_DATE_OR_WEEK', 'BACK_FROM_SELECT_ONE_DAY_DATE', 'BACK_FROM_SELECT_CONTINUOUS_DATE',
    'BACK_FROM_SELECT_RECURRING_DATE', 'USER_REPORT_DEFINE_BOOKING_FROM', 'USER_REPORT_DEFINE_BOOKING_TO', 'GET_USER_REPORT',
    'ALL_USER_REPORT_DEFINE_CREATE_DATE_FROM', 'ALL_USER_REPORT_DEFINE_CREATE_DATE_TO', 'GET_ALL_USER_REPORT',
    'CITY_REPORT_DEFINE_ID', 'CITY_REPORT_DEFINE_BOOKING_FROM', 'CITY_REPORT_DEFINE_BOOKING_TO', 'GET_CITY_REPORT',
    'OFFICE_REPORT_DEFINE_ID', 'OFFICE_REPORT_DEFINE_BOOKING_FROM', 'OFFICE_REPORT_DEFINE_BOOKING_TO', 'GET_OFFICE_REPORT',
    'ALL_OFFICE_REPORT_DEFINE_BOOKING_FROM', 'ALL_OFFICE_REPORT_DEFINE_BOOKING_TO', 'GET_ALL_OFFICE_REPORT',
    'FLOOR_REPORT_DEFINE_FLOOR', 'FLOOR_REPORT_DEFINE_BOOKING_FROM', 'FLOOR_REPORT_DEFINE_BOOKING_TO', 'GET_FLOOR_REPORT'
)   DEFAULT NULL
