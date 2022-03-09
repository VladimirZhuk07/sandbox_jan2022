-- Countries
insert into `Country`(id, name)
value (1, 'UZBEKISTAN');

insert into `Country`(id, name)
value (2, 'BELARUS');

insert into `Country`(id, name)
value (3, 'TURKEY');

insert into `Country`(id, name)
value (4, 'INDIA');

insert into `Country`(id, name)
value (5, 'SWEDEN');

-- Cities
insert into `City`(id, countryId, name)
value (1, 1, 'TASHKENT');

insert into `City`(id, countryId, name)
value (2, 2, 'GOMEL');

insert into `City`(id, countryId, name)
value (3, 3, 'ANKARA');

insert into `City`(id, countryId, name)
value (4, 4, 'NEW-DELI');

insert into `City`(id, countryId, name)
value (5, 5, 'STOCKHOLM');

-- Offices
insert into `Office`(id, cityId, name,
                     address, parking, createdBy,
                     createdDate, modifiedBy, modifiedDate)
values (1, 1, 'UzbekistanExBooker',
         'Aviasozlar-1', true, 'admin',
         NOW(),'admin',NOW() );

insert into `Office`(id, cityId, name,
                     address, parking, createdBy,
                     createdDate, modifiedBy, modifiedDate)
values (2, 2, 'BelarusExBooker',
         'улица Пионерская', true, 'admin',
         NOW(),'admin',NOW() );

insert into `Office`(id, cityId, name,
                     address, parking, createdBy,
                     createdDate, modifiedBy, modifiedDate)
values (3, 3, 'TurkeyЕxBooker',
         'Kocasinan Blv', true, 'admin',
         NOW(),'admin',NOW() );

insert into `Office`(id, cityId, name,
                     address, parking, createdBy,
                     createdDate, modifiedBy, modifiedDate)
values (4, 4, 'IndiaЕxBooker',
         'Lajpat-Nagar', true, 'admin',
         NOW(),'admin',NOW() );

insert into `Office`(id, cityId, name,
                     address, parking, createdBy,
                     createdDate, modifiedBy, modifiedDate)
values (5, 5, 'IndiaЕxBooker',
         'SwedenЕxBooker', true, 'admin',
         NOW(),'admin',NOW() );

-- Maps
insert into `Map`(id, officeId, floorNum,
                 kitchenNum, confRoomsNum)
values (1, 1, 2, 3, 4);

insert into `Map`(id, officeId, floorNum,
                 kitchenNum, confRoomsNum)
values (2, 2, 2, 3, 4);

insert into `Map`(id, officeId, floorNum,
                 kitchenNum, confRoomsNum)
values (3, 3, 2, 3, 4);

insert into `Map`(id, officeId, floorNum,
                 kitchenNum, confRoomsNum)
values (4, 4, 2, 3, 4);

insert into `Map`(id, officeId, floorNum,
                 kitchenNum, confRoomsNum)
values (5, 5, 2, 3, 4);


-- Users
insert into `User`(id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd,
                  telegramState, createdDate,
                  modifiedDate, createdBy, modifiedBy,password)
values (1, null, 'Vladislav',
        'Userovich', 'divergenny@gmail.com', '+998946622397',
        '01.03.2022', null,
        null, NOW(),
        NOW(), 'SYSTEM', 'SYSTEM', '12345');

insert into `UserRole`(userId, roleId)
values (1, 1);


insert into `User`(id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd,
                  telegramState, createdDate,
                  modifiedDate, createdBy, modifiedBy)
values (2, null, 'Azizbek',
        'Systemovich', 'mirzakolonovazizbek@gmail.com', '+998998771917',
        '01.03.2022', null,
        null, NOW(),
        NOW(), 'SYSTEM', 'SYSTEM');

insert into `UserRole`(userId, roleId)
values (2, 2);


insert into `User`(id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd,
                  telegramState, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  password)
values (3, null, 'Ilya',
        'Mapeditorovich', 'divergenny@gmail.com', '+998946622197',
        '01.03.2022', null,
        null, NOW(),
        NOW(), 'SYSTEM', 'SYSTEM',
        '12345');

insert into `UserRole`(userId, roleId)
values (3, 3);


insert into `User`(id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd,
                  telegramState, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  password)
values (4, null, 'Alena',
        'Managerovich', 'mirzakolonovazizbek@gmail.com', '+998998721917',
        '01.03.2022', null,
        null, NOW(),
        NOW(), 'SYSTEM', 'SYSTEM',
        '12345');

insert into `UserRole`(userId, roleId)
values (4, 4);


insert into `User`(id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd,
                  telegramState, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  password)
values (5, null, 'Vladimir',
        'Administratorovich', 'divergenny@gmail.com', '+998998756090',
        '01.03.2022', null,
        null, NOW(),
        NOW(), 'SYSTEM', 'SYSTEM',
        '12345');

insert into `UserRole`(userId, roleId)
values (5, 5);


insert into `User`(id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd,
                  telegramState, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  password)
values (6, null, 'Nurmukhammad',
        'Sunatullaev', 'nurmukhammad.sunatullaev@gmail.com', '+998998756090',
        '01.03.2022', null,
        null, NOW(),
        NOW(), 'SYSTEM', 'SYSTEM',
        'nur123456');

insert into `UserRole`(userId, roleId)
values (6, 5);
