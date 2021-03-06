ALTER TABLE `User`
    MODIFY telegramState        ENUM(
    'GIVE_PHONE_NUMBER', 'MAIN_MENU', 'GET_ACCOUNT_INFO',
    'GET_CONTACT', 'SETTINGS', 'UPDATE_PHONE_NUMBER',
    'MENU', 'CHOOSE_COUNTRY', 'CHOOSE_CITY', 'SHOW_OFFICES_BY_CITY',
    'SHOW_WORKPLACES_BY_OFFICE', 'ASSIGN_BOOKING_TYPE',
    'ONE_DAY_SELECT_DATE', 'CONTINUOUS_SELECT_DATE', 'RECURRING_SELECT_WEEK_DAY',
    'BOOK_ONE_DAY_WORKPLACE', 'CHOOSE_LANGUAGE', 'SET_LANGUAGE', 'REPORT'
)   DEFAULT NULL
